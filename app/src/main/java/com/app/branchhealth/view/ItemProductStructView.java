package com.app.branchhealth.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.branchhealth.R;
import com.app.branchhealth.model.ProductKnowledgeModel;

/**
 * Created by eReFeRHa on 24/3/16.
 */
public class ItemProductStructView {

    // UI
    private View btnProductType;
    private TextView txtProductType;
    private EditText edtQty;
    private Button btnMinus;

    // DATA
    private ProductKnowledgeModel productKnowledgeModel;
    private String qty = "";
    private int position = 0;

    // UI FUNCTION
    public View getBtnProductType() {
        return btnProductType;
    }

    public Button getBtnMinus() {
        return btnMinus;
    }

    public EditText getEdtQty() {
        return edtQty;
    }

    public void setTextProductType(String text){
        txtProductType.setText(text);
    }

    public void setTextQty(String text){
        edtQty.setText(text);
    }

    public View getView(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.item_product_type, null);
        btnProductType = rootView.findViewById(R.id.btnProductType);
        txtProductType = (TextView) rootView.findViewById(R.id.txtProductType);
        edtQty = (EditText) rootView.findViewById(R.id.edtQty);
        btnMinus = (Button) rootView.findViewById(R.id.btnMinus);

        if(productKnowledgeModel != null){
            setTextProductType(productKnowledgeModel.getProductName());
        }

        if(qty != null){
            edtQty.setText(qty);
        }

        return rootView;
    }

    public ProductKnowledgeModel getProductKnowledgeModel() {
        return productKnowledgeModel;
    }

    public void setProductKnowledgeModel(ProductKnowledgeModel productKnowledgeModel) {
        this.productKnowledgeModel = productKnowledgeModel;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
