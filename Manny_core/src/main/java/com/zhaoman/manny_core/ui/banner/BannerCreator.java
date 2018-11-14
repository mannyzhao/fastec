package com.zhaoman.manny_core.ui.banner;



import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zhaoman.manny_core.R;

import java.util.ArrayList;

/**
 * Author:zhaoman
 * Date:2018/11/14
 * Description:
 */
public class BannerCreator {

    public static void setDefault(ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners,
                                  OnItemClickListener clickListener){


       convenientBanner.setPages(new HolderCreator(),banners)
               .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
               .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
               .setOnItemClickListener(clickListener)
               .setPageTransformer(new DefaultTransformer())
               .startTurning(3000)
               .setCanLoop(true);

    }
}