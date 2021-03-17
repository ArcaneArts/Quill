# Quill
General Java Library typically for servers.

[![Release](https://jitpack.io/v/ArcaneArts/Quill.svg)](https://jitpack.io/#ArcaneArts/Quill)

```groovy
maven { url 'https://jitpack.io' }
```

```groovy
implementation 'com.github.ArcaneArts:Quill:latest.release'
```

# Getting Started (Quick Reference)

Some common things you might like to know

## Standalone Server Bootstrap

```java
package art.arcane.quill.service;

import art.arcane.quill.Quill;
import art.arcane.quill.logging.L;

public class TestService extends QuillService
{
    public static void main(String[] a)
    {
        Quill.start(a);
    }

    // You only need to initialize defaults if you have configurable options
    @ServiceWorker
    private TestServiceWorker testServiceWorker = new TestServiceWorker();

    @Override
    public void onEnable() {
        L.v("Enabled!");
    }

    @Override
    public void onDisable() {
        L.v("Disabled!");
    }
}
```

```java
package art.arcane.quill.service;

import art.arcane.quill.logging.L;

public class TestServiceWorker extends QuillService {
    @Override
    public void onEnable() {
        i("Worker Enabled!");
    }

    @Override
    public void onDisable() {
        i("Worker Disabled!");
    }
}
```

## Console & Console Command Registry

Simply add the console service worker to any service (or service worker)

```java
// Initialize the console if you want it configurable
@ServiceWorker
private ConsoleService console = new ConsoleService();

@Override
public void onEnable() {
    // Register commands onEnable
    console.registerCommand("foo", params -> {
        i("Bar!");
        // TRUE = handled, FALSE = try another command (for duplicate names)
        return true;
    });

    console.registerCommand("echo", params -> {
        i(KList.from(params).toString(" "));
        return true;
    });
}
```

## Service Configuration

Simply add a variable to your service/worker class. So long as it's not transient, static or final. Define variable values for config defaults. This system uses fragmented gson, so it can handle lists of objects nested inside of each other and so on.

Configurable options will not generate unless your registered services have a default instance when you declare it in your field.

```java
private int someConfigInt = 34;
private String someConfigOption = "aabbcc";
private SomeEnum someEnumOption = SomeEnum.OPTION_B;
private DataObject someBigObject = new DataObject();
```
