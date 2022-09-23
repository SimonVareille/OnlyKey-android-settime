# OnlyKey Android Set Time

![icon](icon.svg)

Set the time of a connected OnlyKey with Android.

## Overview

TOTP generation requires the OnlyKey to know the current UNIX time. The [WebCrypt](https://apps.crp.to)
web app is meant to perform this task.

In some cases you don't want to rely on a website to set the time. *Onlykey Set Time* can
thus be used for this, in a convenient interaction-less behaviour.

## Usage

1. Install the app;
2. Connect your OnlyKey to your smartphone;
3. Allow *OnlyKey Set Time* to be triggered each time an OnlyKey is inserted.

Now, *OnlyKey Set Time* will be invoked every time an OnlyKey is plugged. The time will be set as
soon as the key is available, before it is unlocked.
