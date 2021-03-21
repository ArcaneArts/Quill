/*
 * This file is part of Quill by Arcane Arts.
 *
 * Quill by Arcane Arts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Quill by Arcane Arts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License in this package for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quill.  If not, see <https://www.gnu.org/licenses/>.
 */

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
