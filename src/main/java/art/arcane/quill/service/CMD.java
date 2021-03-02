package art.arcane.quill.service;

@FunctionalInterface
public interface CMD
{
    public boolean onCommand(String... params);
}
