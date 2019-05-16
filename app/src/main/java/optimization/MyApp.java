package optimization;

import android.app.Application;

/**
 * Created on 2019/5/16.16:00
 *
 * @author
 * @desc
 */
public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        try {
            InitializeService.enqueueWork(this);
        } catch (Exception ignore) {

        }
    }
}
