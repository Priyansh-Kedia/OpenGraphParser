

# OpenGraphParser
[![](https://jitpack.io/v/Priyansh-Kedia/OpenGraphParser.svg)](https://jitpack.io/#Priyansh-Kedia/OpenGraphParser)
 <a href="https://devlibrary.withgoogle.com/products/android/repos/Priyansh-Kedia-OpenGraphParser"><img alt="Google" src="https://raw.githubusercontent.com/Priyansh-Kedia/OpenGraphParser/master/images/google-devlib.svg"/>
</a>[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
<a 
href="https://proandroiddev.com/how-to-create-a-preview-for-a-link-in-android-6906d0aa9e12"><img alt="ProAndroidDev" src="https://raw.githubusercontent.com/Priyansh-Kedia/OpenGraphParser/master/images/%20Story-Medium.svg"/></a>

A small and easy to use library which provides the convenience of using [Open Graph Protocol](https://ogp.me/) in Android very easily. 
Create previews for links to share in Android very easily.




### Star this repo to show your support [stargazers](https://github.com/Priyansh-Kedia/OpenGraphParser/stargazers) for this repository. :star2:


https://user-images.githubusercontent.com/52661249/127740097-51535912-6623-48ac-8a3f-7709ac9b968e.mp4



Add this in your root build.gradle at the end of repositories:

		allprojects {
			repositories {
			...
				maven { url 'https://jitpack.io' }
			}
		}

Add the dependency

	dependencies {
		   implementation 'com.github.Priyansh-Kedia:OpenGraphParser:<latest_version>'
		}


### If using ProGuard add this line to your proguard-rules.pro:

    -keep public class org.jsoup.** {  
	    public *;  
    }


# Implementation

    private val openGraphParser = OpenGraphParser(this, showNullOnEmpty = true, cacheProvider = OpenGraphCacheProvider(this))
	
	openGraphParser.parse(linkUrl) // To parse the link provided

or you can use the Builder to create the instance of the class if you need to specify additional values. You can do so like this

	OpenGraphParser.Builder(listener = this)
	        .cacheProvider(OpenGraphCacheProvider(this))
	        .showNullOnEmpty(true)
	        .maxBodySize(1000000)
	        .timeout(1000000)
	        .jsoupProxy(JsoupProxy("<host>", 8000))
	        .build()

> Note: All values can be specified using the default constructor also

Values that can be specified using the Builder pattern are

> listener:- Specified [here](#listener-opengraphcallback)

> showNullOnEmpty:- Specified [here](#shownullonempty)

> cacheProvider:- Specified [here](#cacheprovider)

> maxBodySize:- max body size to fetch from internet (default 1000000 bytes)

> timeout:- timeout for network request (default 60000 ms)

> jsoupProxy:- Specify proxy while fetching from internet. This will be an instance of JsoupProxy to specify host and port. See: [JsoupProxy](#jsoupproxy-class)


##### Listener (OpenGraphCallback)
The class requires you to implement two callback functions,`onError(error: String)` and `onPostResponse(openGraphResult: OpenGraphResult)`. The former is invoked in case of error (incorrect url), and the latter is invoked on successful response.

##### showNullOnEmpty
The `showNullOnEmpty` is an optional parameter, with a default value of `false`. If set to `true`, the parser would invoke `onError` if the `title` and `description` are empty for the link provided.

##### CacheProvider
The `cacheProvider` is also an optional parameter.  It can be passed in two ways :-
 1. The default `OpenGraphCacheProvider`, which implements `SharedPreferences` from the library itself.
 2. A custom Shared Preferences class which should implement the interface `CacheProvider`.  The `CacheProvider` interface provides two callback functions `getOpenGraphResult` and `setOpenGraphResult`, where `getOpenGraphResult` is used to write to the cache, and `setOpenGraphResult` is used to read from the cache. 

---------

Inside `onPostResponse(openGraphResult: OpenGraphResult)` you can use the data to show on your UI like this. 

    override fun onPostResponse(openGraphResult: OpenGraphResult) {  
    	linkPreviewLayout.apply {  
			Glide.with(this@ChannelActivity).load(openGraphResult.image).into(linkImage)  
			linkTitle.text = openGraphResult.title  
			linkDescription.text = openGraphResult.description  
			website.text = openGraphResult.siteName  
	 	}}

## OpenGraphResult class
The data class ***OpenGraphResult*** contains:

 - title -> The title of the page the link points to
 - description -> The description metadata of the page
 - url -> The url of the page
 - image -> The image metadata for the page
 - siteName -> The name of the website (BASE URL).
 - type -> The type of the object e.g., "video.movie".

## JsoupProxy class
The data class ***JsoupProxy*** contains: 
- host -> The host for the proxy
- port -> The port for the proxy


# Contributions
- Fork the repo
- Create a new branch and make changes
- Push the code to the branch and make a PR! :+1:

# License
MIT License

Copyright (c) 2021 Priyansh Kedia

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.



### Found this library useful? :heart:

Support it by joining [stargazers](https://github.com/Priyansh-Kedia/OpenGraphParser/stargazers) for this repository. :star2:
