package art.arcane.quill;

import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.Service;
import art.arcane.quill.service.services.ConsoleService;
import art.arcane.quill.service.services.SchedulerService;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuillTestService extends QuillService {
    @Service
    private ConsoleService console = new ConsoleService();

    @Service
    private SchedulerService scheduler = new SchedulerService();

    public static void startTestService() {
        Quill.start(new String[0]);
    }

    @Override
    public void onEnable() {
        i("Test Service Started");
    }

    @Override
    public void onDisable() {
        i("Test Service Stopped");
    }
}
