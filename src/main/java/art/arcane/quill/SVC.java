package art.arcane.quill;

import art.arcane.quill.service.IService;

public class SVC
{
	public static <T extends IService> T get(Class<? extends T> c)
	{
		return Quill.getService(c);
	}
}
