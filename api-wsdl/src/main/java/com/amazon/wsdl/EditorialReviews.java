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
 *         &lt;element ref="{http://webservices.amazon.com/AWSECommerceService/2013-08-01}EditorialReview" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "editorialReview"
})
@XmlRootElement(name = "EditorialReviews")
public class EditorialReviews {

    @XmlElement(name = "EditorialReview")
    protected List<EditorialReview> editorialReview;

    /**
     * Gets the value of the editorialReview property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the editorialReview property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEditorialReview().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EditorialReview }
     */
    public List<EditorialReview> getEditorialReview() {
        if (editorialReview == null) {
            editorialReview = new ArrayList<EditorialReview>();
        }
        return this.editorialReview;
    }

}