package com.amazon.wsdl;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SubTotal" type="{http://webservices.amazon.com/AWSECommerceService/2013-08-01}Price" minOccurs="0"/>
 *         &lt;element name="CartItem" type="{http://webservices.amazon.com/AWSECommerceService/2013-08-01}CartItem" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "subTotal",
        "cartItem"
})
@XmlRootElement(name = "CartItems")
public class CartItems {

    @XmlElement(name = "SubTotal")
    protected Price subTotal;
    @XmlElement(name = "CartItem", required = true)
    protected List<CartItem> cartItem;

    /**
     * Gets the value of the subTotal property.
     *
     * @return possible object is
     * {@link Price }
     */
    public Price getSubTotal() {
        return subTotal;
    }

    /**
     * Sets the value of the subTotal property.
     *
     * @param value allowed object is
     *              {@link Price }
     */
    public void setSubTotal(Price value) {
        this.subTotal = value;
    }

    /**
     * Gets the value of the cartItem property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cartItem property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCartItem().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CartItem }
     */
    public List<CartItem> getCartItem() {
        if (cartItem == null) {
            cartItem = new ArrayList<CartItem>();
        }
        return this.cartItem;
    }

}
