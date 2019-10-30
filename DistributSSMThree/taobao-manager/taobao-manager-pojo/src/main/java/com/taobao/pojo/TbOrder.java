package com.taobao.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbOrder implements Serializable{
    private String orderId;

    private String payment;

    private Integer paymentType;

    private String postFee;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date paymentTime;

    private Date consignTime;

    private Date endTime;

    private Date closeTime;

    private String shippingName;

    private String shippingCode;

    private Long userId;

    private String buyerMessage;

    private String buyerNick;

    private Integer buyerRate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee == null ? null : postFee.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode == null ? null : shippingCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage == null ? null : buyerMessage.trim();
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }

    public Integer getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(Integer buyerRate) {
        this.buyerRate = buyerRate;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyerMessage == null) ? 0 : buyerMessage.hashCode());
		result = prime * result + ((buyerNick == null) ? 0 : buyerNick.hashCode());
		result = prime * result + ((buyerRate == null) ? 0 : buyerRate.hashCode());
		result = prime * result + ((closeTime == null) ? 0 : closeTime.hashCode());
		result = prime * result + ((consignTime == null) ? 0 : consignTime.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((paymentTime == null) ? 0 : paymentTime.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((postFee == null) ? 0 : postFee.hashCode());
		result = prime * result + ((shippingCode == null) ? 0 : shippingCode.hashCode());
		result = prime * result + ((shippingName == null) ? 0 : shippingName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TbOrder other = (TbOrder) obj;
		if (buyerMessage == null) {
			if (other.buyerMessage != null)
				return false;
		} else if (!buyerMessage.equals(other.buyerMessage))
			return false;
		if (buyerNick == null) {
			if (other.buyerNick != null)
				return false;
		} else if (!buyerNick.equals(other.buyerNick))
			return false;
		if (buyerRate == null) {
			if (other.buyerRate != null)
				return false;
		} else if (!buyerRate.equals(other.buyerRate))
			return false;
		if (closeTime == null) {
			if (other.closeTime != null)
				return false;
		} else if (!closeTime.equals(other.closeTime))
			return false;
		if (consignTime == null) {
			if (other.consignTime != null)
				return false;
		} else if (!consignTime.equals(other.consignTime))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (paymentTime == null) {
			if (other.paymentTime != null)
				return false;
		} else if (!paymentTime.equals(other.paymentTime))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (postFee == null) {
			if (other.postFee != null)
				return false;
		} else if (!postFee.equals(other.postFee))
			return false;
		if (shippingCode == null) {
			if (other.shippingCode != null)
				return false;
		} else if (!shippingCode.equals(other.shippingCode))
			return false;
		if (shippingName == null) {
			if (other.shippingName != null)
				return false;
		} else if (!shippingName.equals(other.shippingName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TbOrder [orderId=" + orderId + ", payment=" + payment + ", paymentType=" + paymentType + ", postFee="
				+ postFee + ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", paymentTime=" + paymentTime + ", consignTime=" + consignTime + ", endTime=" + endTime
				+ ", closeTime=" + closeTime + ", shippingName=" + shippingName + ", shippingCode=" + shippingCode
				+ ", userId=" + userId + ", buyerMessage=" + buyerMessage + ", buyerNick=" + buyerNick + ", buyerRate="
				+ buyerRate + "]";
	}
    
}