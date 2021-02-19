package art.arcane.quill.config;

import art.arcane.quill.json.JSONObject;

public interface Writable
{
	public void fromJSON(JSONObject j);

	public JSONObject toJSON();
}
