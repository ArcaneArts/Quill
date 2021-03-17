package art.arcane.quill.service.util;

@FunctionalInterface
public interface CMD
{
    public boolean onCommand(String... params);
}
