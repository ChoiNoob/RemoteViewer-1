package com.damintsev.client;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.UriUtils;
import com.sencha.gxt.core.client.util.IconHelper;

/**
 * User: Damintsev Andrey
 * Date: 05.08.13
 * Time: 22:23
 */
public class Utils {

    public static ImageResource getImage(String name) {
        if ("hipath".equals(name))
            return IconHelper.getImageResource(UriUtils.fromString("web/img/hipath4000.jpg"), 127, 211);
        if ("logo".equals(name))
            return IconHelper.getImageResource(UriUtils.fromString("web/img/avanti_logo_64.png"), 64, 64);
        if ("tooltip_image".equals(name))
            return IconHelper.getImageResource(UriUtils.fromString("web/img/tooltip_image.png"), 3, 1);
        if ("hipath3800".equals(name))
            return IconHelper.getImageResource(UriUtils.fromString("web/img/hipath3800.png"), 75, 84);
         if ("cloud_130".equals(name))
            return IconHelper.getImageResource(UriUtils.fromString("web/img/cloud_130.png"), 130, 130);
        if ("hipath3800_32".equals(name))
            return IconHelper.getImageResource(UriUtils.fromString("web/img/hipath3800_32.png"), 32, 32);

        return null;
    }
}
