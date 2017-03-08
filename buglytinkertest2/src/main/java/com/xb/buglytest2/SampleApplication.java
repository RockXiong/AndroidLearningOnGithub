package com.xb.buglytest2;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by Administrator on 2017/3/8.
 */

public class SampleApplication extends TinkerApplication
{
    public SampleApplication()
    {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.xb.buglytest2.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
