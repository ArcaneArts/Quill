package art.arcane.quill.service.test;

import art.arcane.quill.execution.J;
import art.arcane.quill.logging.L;
import art.arcane.quill.math.M;
import art.arcane.quill.random.RNG;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.Service;
import art.arcane.quill.service.services.SchedulerService;
import art.arcane.quill.service.util.RepeatingTask;
import art.arcane.quill.service.util.SingularTask;

public class ChildServiceC extends QuillService
{
    @Service
    private SchedulerService scheduler = new SchedulerService();

    private int c = 0;
    @Override
    public void onEnable() {
        v("My Parent is " + getParent().getServiceName());

        for(int i = 0; i < 2000; i++)
        {
            scheduler.queue(SingularTask.now(() -> {
                J.sleep(6 + RNG.r.i(11));

                if(M.r(0.1))
                {
                    J.sleep(RNG.r.i(800));
                }

                if(M.r(0.01))
                {
                    J.sleep(RNG.r.i(8000));
                }

                if(M.r(0.07))
                {
                    scheduler.queue(SingularTask.after(RNG.r.i(1000), () -> J.sleep(RNG.r.i(1000))));
                }
            }));
        }
    }

    @Override
    public void onDisable() {
    }
}
