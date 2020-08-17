# Help improve translation

We'd like you to help translate the app into your spoken language.

Well, before we get started, you may want to learn a bit about the `xml` file format which our translation files use.
You can just think it as an ordianary text file so you can skip the section if it bored you.

# Xml file format

To be simple, you're only supposed to translate texts between a `<string>` and `</string>` things. Suppose the English version is:

```xml
<string name="meow">Meow~</string>
```

You're supposed to translate it to:

```xml
<string name="meow">喵~</string>
```

But **not**:

```xml
<string name="喵">喵~</string>
```

And if there're `a href` or `xliff` things that are wrapped in `<` and `>` you should keep them as-is.
But don't worry, even if you messed them, we'll still accept the translation and have them recovered.

To learn more, [google it](https://www.google.com/?q=xml%20format).

You may want an advanced text editor like [Notepad++](https://notepad-plus-plus.org/) other than your PC's
default notepad app.

## Confirm existing translation

Right click [here](https://github.com/oO0oO0oO0o0o00/blocktopograph/tree/master/app/src/main/res) and open in new tab.
In the list of folders you can see folders with names like "values-[xx]" where [xx] would be language or locale code,
e.g. "zh-rCN" for "Chinese Simplified (China)" and "ru" for "Russian". For a list of such thing, just google.
If there is a folder for your language then within the folder there would be a `strings.xml` within,
which is a translation file of your language. 

If any translation should be improved,
you can either :

* Download the file and edit offline.
* Or fork the repository if you've worked on Github before and know how to.

## Create new translation

If you didn't find your language folder then :

* [This file](https://github.com/oO0oO0oO0o0o00/blocktopograph/blob/master/app/src/main/res/values/strings.xml) is the English version. You can [download it](https://github.com/oO0oO0oO0o0o00/blocktopograph/raw/master/app/src/main/res/values/strings.xml) and edit offline.
* Fork the repository and do the localization if you're Android dev.

## Submission

1. If you've downloaded and edited offline, mail it to barcodephantomAToutlook.com, replace "AT" with "@".
2. Otherwise create a pull request.

Note that :

1. You **don't** have to translate everything before submitting.
2. Better clearify what language it is in, and if it's newly created.

Ahh, no one will be looking at this, right?
