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

package art.arcane.quill.config;

import art.arcane.quill.json.JSONObject;

public abstract class WritableObject implements Writable {
    public abstract void toJSON(JSONObject o);

    @Override
    public abstract void fromJSON(JSONObject j);

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        toJSON(j);
        return j;
    }

}
