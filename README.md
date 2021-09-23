
# OpenGraphParser
[![](https://jitpack.io/v/Priyansh-Kedia/OpenGraphParser.svg)](https://jitpack.io/#Priyansh-Kedia/OpenGraphParser)


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





# Implementation

    private val openGraphParser = OpenGraphParser(this, showNullOnEmpty = true, context = this)
	
	openGraphParser.parse(linkUrl) // To parse the link provided

The class required you to implement two callback functions, `onError(error: String)` and `onPostResponse(openGraphResult: OpenGraphResult)`. The former is invoked in case of error (incorrect url), and the latter is invoked on successful response. 

The `showNullOnEmpty` is an optional parameter, with a default value of `false`. If set to `true`, the parser would invoke `onError` if the `title` and `description` are empty for the link provided.

The `context` is also an optional parameter. If context is provided, then caching is implemented, and link preview can be generated faster.

The data class ***OpenGraphResult*** contains:
			

 - title -> The title of the page the link points to
 - description -> The description metadata of the page
 - url -> The url of the page
 - image -> The image metadata for the page
 - siteName -> The name of the website (BASE URL).
 - type -> The type of the object e.g., "video.movie".

Inside `onPostResponse(openGraphResult: OpenGraphResult)` you can use the data to show on your UI like this. 

    override fun onPostResponse(openGraphResult: OpenGraphResult) {  
    	linkPreviewLayout.apply {  
			Glide.with(this@ChannelActivity).load(openGraphResult.image).into(linkImage)  
			linkTitle.text = openGraphResult.title  
			linkDescription.text = openGraphResult.description  
			website.text = openGraphResult.siteName  
	 	}}


# Contributions
- Fork the repo
- Create a new branch and make changes
- Push the code to the branch and make a PR! :+1:

# License
Copyright 2021 Priyansh Kedia

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


### Found this library useful? :heart:

Support it by joining [stargazers](https://github.com/Priyansh-Kedia/OpenGraphParser/stargazers) for this repository. :star2:
