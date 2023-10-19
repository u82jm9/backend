package com.homeapp.nonsense_BE.models.bike;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Entity
@Table(name = "Parts")
public class BikeParts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PartsId")
    private long partsId;

    @Column(name = "Frame")
    private String frameName;

    @Column(name = "FramePrice")
    private BigDecimal framePrice;

    @Column(name = "ChainName")
    private String chainName;

    @Column(name = "ChainPrice")
    private BigDecimal chainPrice;

    @Column(name = "CassetteName")
    private String cassetteName;

    @Column(name = "CassettePrice")
    private BigDecimal cassettePrice;

    @Column(name = "RearDeraileurName")
    private String rearDeraileurName;

    @Column(name = "RearDeraileurPrice")
    private BigDecimal rearDeraileurPrice;

    @Column(name = "FrontDeraileurName")
    private String frontDeraileurName;

    @Column(name = "FrontDeraileurPrice")
    private BigDecimal frontDeraileurPrice;

    @Column(name = "ChainringName")
    private String chainringName;

    @Column(name = "ChainringPrice")
    private BigDecimal chainringPrice;

    @Column(name = "BrakesName")
    private String brakesName;

    @Column(name = "BrakesPrice")
    private BigDecimal brakesPrice;

    @Column(name = "BrakeLeverName")
    private String brakeLeverName;

    @Column(name = "BrakeLeverPrice")
    private BigDecimal brakeLeverPrice;

    @Column(name = "ShifterName")
    private String shifterName;

    @Column(name = "ShifterPrice")
    private BigDecimal shifterPrice;

    @Column(name = "HandlebarName")
    private String handlebarName;

    @Column(name = "HandlebarPrice")
    private BigDecimal handlebarPrice;

    @Column(name = "Links")
    private ArrayList<String> weblinks;

    @Column(name = "TotalPrice")
    private BigDecimal totalBikePrice;

    public BikeParts() {
    }

    public BikeParts(String frameName, BigDecimal framePrice, String chainName, BigDecimal chainPrice, String cassetteName, BigDecimal cassettePrice, String rearDeraileurName, BigDecimal rearDeraileurPrice, String frontDeraileurName, BigDecimal frontDeraileurPrice, String chainringName, BigDecimal chainringPrice, String brakesName, BigDecimal brakesPrice, String brakeLeverName, BigDecimal brakeLeverPrice, String shifterName, BigDecimal shifterPrice, String handlebarName, BigDecimal handlebarPrice, ArrayList<String> weblinks, BigDecimal totalBikePrice) {
        this.frameName = frameName;
        this.framePrice = framePrice;
        this.chainName = chainName;
        this.chainPrice = chainPrice;
        this.cassetteName = cassetteName;
        this.cassettePrice = cassettePrice;
        this.rearDeraileurName = rearDeraileurName;
        this.rearDeraileurPrice = rearDeraileurPrice;
        this.frontDeraileurName = frontDeraileurName;
        this.frontDeraileurPrice = frontDeraileurPrice;
        this.chainringName = chainringName;
        this.chainringPrice = chainringPrice;
        this.brakesName = brakesName;
        this.brakesPrice = brakesPrice;
        this.brakeLeverName = brakeLeverName;
        this.brakeLeverPrice = brakeLeverPrice;
        this.shifterName = shifterName;
        this.shifterPrice = shifterPrice;
        this.handlebarName = handlebarName;
        this.handlebarPrice = handlebarPrice;
        this.weblinks = weblinks;
        this.totalBikePrice = totalBikePrice;
    }

    public long getPartsId() {
        return partsId;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public BigDecimal getFramePrice() {
        return framePrice;
    }

    public void setFramePrice(BigDecimal framePrice) {
        this.framePrice = framePrice;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public BigDecimal getChainPrice() {
        return chainPrice;
    }

    public void setChainPrice(BigDecimal chainPrice) {
        this.chainPrice = chainPrice;
    }

    public String getCassetteName() {
        return cassetteName;
    }

    public void setCassetteName(String cassetteName) {
        this.cassetteName = cassetteName;
    }

    public BigDecimal getCassettePrice() {
        return cassettePrice;
    }

    public void setCassettePrice(BigDecimal cassettePrice) {
        this.cassettePrice = cassettePrice;
    }

    public String getRearDeraileurName() {
        return rearDeraileurName;
    }

    public void setRearDeraileurName(String rearDeraileurName) {
        this.rearDeraileurName = rearDeraileurName;
    }

    public BigDecimal getRearDeraileurPrice() {
        return rearDeraileurPrice;
    }

    public void setRearDeraileurPrice(BigDecimal rearDeraileurPrice) {
        this.rearDeraileurPrice = rearDeraileurPrice;
    }

    public String getFrontDeraileurName() {
        return frontDeraileurName;
    }

    public void setFrontDeraileurName(String frontDeraileurName) {
        this.frontDeraileurName = frontDeraileurName;
    }

    public BigDecimal getFrontDeraileurPrice() {
        return frontDeraileurPrice;
    }

    public void setFrontDeraileurPrice(BigDecimal frontDeraileurPrice) {
        this.frontDeraileurPrice = frontDeraileurPrice;
    }

    public String getChainringName() {
        return chainringName;
    }

    public void setChainringName(String chainringName) {
        this.chainringName = chainringName;
    }

    public BigDecimal getChainringPrice() {
        return chainringPrice;
    }

    public void setChainringPrice(BigDecimal chainringPrice) {
        this.chainringPrice = chainringPrice;
    }

    public String getBrakesName() {
        return brakesName;
    }

    public void setBrakesName(String brakesName) {
        this.brakesName = brakesName;
    }

    public BigDecimal getBrakesPrice() {
        return brakesPrice;
    }

    public void setBrakesPrice(BigDecimal brakesPrice) {
        this.brakesPrice = brakesPrice;
    }

    public String getBrakeLeverName() {
        return brakeLeverName;
    }

    public void setBrakeLeverName(String brakeLeverName) {
        this.brakeLeverName = brakeLeverName;
    }

    public BigDecimal getBrakeLeverPrice() {
        return brakeLeverPrice;
    }

    public void setBrakeLeverPrice(BigDecimal brakeLeverPrice) {
        this.brakeLeverPrice = brakeLeverPrice;
    }

    public String getShifterName() {
        return shifterName;
    }

    public void setShifterName(String shifterName) {
        this.shifterName = shifterName;
    }

    public BigDecimal getShifterPrice() {
        return shifterPrice;
    }

    public void setShifterPrice(BigDecimal shifterPrice) {
        this.shifterPrice = shifterPrice;
    }

    public String getHandlebarName() {
        return handlebarName;
    }

    public void setHandlebarName(String handlebarName) {
        this.handlebarName = handlebarName;
    }

    public BigDecimal getHandlebarPrice() {
        return handlebarPrice;
    }

    public void setHandlebarPrice(BigDecimal handlebarPrice) {
        this.handlebarPrice = handlebarPrice;
    }

    public ArrayList<String> getWeblinks() {
        return weblinks;
    }

    public void setWeblinks(ArrayList<String> weblinks) {
        this.weblinks = weblinks;
    }

    public BigDecimal getTotalBikePrice() {
        return totalBikePrice;
    }

    public void setTotalBikePrice(BigDecimal totalBikePrice) {
        this.totalBikePrice = totalBikePrice;
    }

    @Override
    public String toString() {
        return "BikeParts{" +
                "partsId=" + partsId +
                ", frameName='" + frameName + '\'' +
                ", framePrice=" + framePrice +
                ", chainName='" + chainName + '\'' +
                ", chainPrice=" + chainPrice +
                ", cassetteName='" + cassetteName + '\'' +
                ", cassettePrice=" + cassettePrice +
                ", rearDeraileurName='" + rearDeraileurName + '\'' +
                ", rearDeraileurPrice=" + rearDeraileurPrice +
                ", frontDeraileurName='" + frontDeraileurName + '\'' +
                ", frontDeraileurPrice=" + frontDeraileurPrice +
                ", chainringName='" + chainringName + '\'' +
                ", chainringPrice=" + chainringPrice +
                ", brakesName='" + brakesName + '\'' +
                ", brakesPrice=" + brakesPrice +
                ", brakeLeverName='" + brakeLeverName + '\'' +
                ", brakeLeverPrice=" + brakeLeverPrice +
                ", shifterName='" + shifterName + '\'' +
                ", shifterPrice=" + shifterPrice +
                ", handlebarName='" + handlebarName + '\'' +
                ", handlebarPrice=" + handlebarPrice +
                ", weblinks=" + weblinks +
                ", totalBikePrice=" + totalBikePrice +
                '}';
    }
}