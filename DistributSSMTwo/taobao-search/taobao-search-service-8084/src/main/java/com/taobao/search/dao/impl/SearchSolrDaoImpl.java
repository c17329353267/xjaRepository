package com.taobao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taobao.pojo.SearchSolrItem;
import com.taobao.pojo.SearchSolrResults;
import com.taobao.search.dao.SearchSolrDao;

@Repository
public class SearchSolrDaoImpl implements SearchSolrDao{

	@Autowired
	private CloudSolrServer cloudSolrServer;
	@Override
	public SearchSolrResults querySearch(SolrQuery solrQuery) throws Exception {
		// 创建solr响应对象
		System.out.println("solrServer:"+cloudSolrServer.toString());
		QueryResponse queryResponse = cloudSolrServer.query(solrQuery);
		SolrDocumentList documentList = queryResponse.getResults();
		
		//用户存放查询出来的item实体类数据信息
		List<SearchSolrItem> items = new ArrayList<SearchSolrItem>();
		
		//创建分页对象
		
		if(documentList != null){
			//获取总记录数给分页赋值
			
			for (SolrDocument document : documentList) {
				
				SearchSolrItem solrItem = new SearchSolrItem();
				solrItem.setId((String)document.get("id"));
				solrItem.setImage(this.splitImgUrl((String)document.get("item_image")));
				//solrItem.setImage((String)document.get("item_image"));
				System.out.println("solrItem="+solrItem+";documet=="+document);
				solrItem.setPrice((Long)document.get("item_price"));
			
				//由于只给标题进行高亮显示
				//存在此标题即高亮覆盖否则原样显示
				Map<String, Map<String, List<String>>> highliting = queryResponse.getHighlighting();
				
				if(highliting != null){
					List<String> listtitle = highliting.get(document.get("id")).get("item_title");
					if(listtitle != null&&listtitle.size() >0){
						solrItem.setTitle(listtitle.get(0));
						}else{
							solrItem.setTitle((String) document.get("item_title"));
						}	
					}
					else {
						solrItem.setTitle((String) document.get("item_title"));
	
					}
				items.add(solrItem);
			}
			

		}
		SearchSolrResults solrResults = new SearchSolrResults();
		solrResults.setRecordCount(documentList.getNumFound());
		//将查询出的结果放入到集合中
		solrResults.setListSearchItems(items);
		return solrResults;
	}
	
	//分割图片字符串
	public String splitImgUrl(String imgUrl){
		String img = imgUrl.split(",")[0];
		System.out.println("splitImageUrl:"+img);
		return img;
	}

}
