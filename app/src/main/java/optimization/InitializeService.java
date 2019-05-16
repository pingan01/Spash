package optimization;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.lkkj.spash.BuildConfig;

/**
 * @author Administrator
 */
public class InitializeService extends JobIntentService {

    public static final int JOB_ID = 0x001;

    private static final String INIT_ACTION = BuildConfig.APPLICATION_ID + "service.action.INIT";

    private static boolean isInit = false;

    public static void enqueueWork(Context context) {
        Intent work = new Intent();
        work.setAction(INIT_ACTION);
        enqueueWork(context, InitializeService.class, JOB_ID, work);
    }

    private void init() {
        if (isInit) {
            return;
        }
        isInit = true;
        //第三方初始化
    }

    public InitializeService() {
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
        if (!INIT_ACTION.equals(action)) {
            return;
        }
        init();
    }


}
