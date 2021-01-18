package com.example.pdfsave;

import android.os.Bundle;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;

public class ZoomablePDFActivityPhotoView extends BaseSampleActivity {
    PDFViewPager pdfViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.zoomable_asset_on_xml);
        setContentView(R.layout.activity_zoomable_pdf_xml_photoview);
        pdfViewPager = (PDFViewPager) findViewById(R.id.pdfViewPagerZoom);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BasePDFPagerAdapter adapter = (BasePDFPagerAdapter) pdfViewPager.getAdapter();
        if (adapter != null) {
            adapter.close();
        }
    }
}
