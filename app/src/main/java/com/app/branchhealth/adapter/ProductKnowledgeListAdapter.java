package com.app.branchhealth.adapter;

import android.content.Context;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.app.branchhealth.R;
import com.app.branchhealth.model.ProductKnowledgeModel;
import com.app.branchhealth.services.SingletonHTTPRequest;

import java.util.List;

import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselItem;

/**
 * Created by eReFeRHa on 17/3/16.
 */
public class ProductKnowledgeListAdapter extends CarouselAdapter<ProductKnowledgeModel> {

    public ProductKnowledgeListAdapter(Context context, List<ProductKnowledgeModel> items) {
        super(context, items);
    }

    @Override
    public CarouselItem<ProductKnowledgeModel> getCarouselItem(Context context) {
        return new ProdKnowledgeCarouselItem(context);
    }

    class ProdKnowledgeCarouselItem extends CarouselItem<ProductKnowledgeModel> {

        private Context context;
        private NetworkImageView itemImage;

        public ProdKnowledgeCarouselItem(Context context) {
            super(context, R.layout.item_product_knowledge);
            this.context = context;
        }

        @Override
        public void extractView(View view) {
            itemImage = (NetworkImageView) view.findViewById(R.id.itemImage);
        }

        @Override
        public void update(ProductKnowledgeModel productKnowledgeModel) {
            itemImage.setDefaultImageResId(productKnowledgeModel.getDefaultImage());
            itemImage.setImageResource(productKnowledgeModel.getDefaultImage());
            itemImage.setImageUrl(productKnowledgeModel.getUrl(), SingletonHTTPRequest.getInstance(context).getImageLoader());
        }
    }
}
