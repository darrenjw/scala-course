# Smile as a Scala library

Redoing the yacht hydrodynamics OLS regression analyis using [Smile](https://haifengl.github.io/). If you want to do simple statistical modelling and machine learning for small-to-medium sized data sets in Scala, then this currently looks to be a good option, so is probably worth spending some time getting to grips with it.

This example also illustrates the use of Scala's [mdoc](https://scalameta.org/mdoc/) Markdown-based documentation system. After enabling the plugin, Markdown files in [docs](docs/) get compiled to [target/mdoc](target/mdoc/). 


At the time of writing (February 2020), the current version of Smile (2.1.1) only works with Scala 2.13. This is just because of a problem with the build, and not for any fundamental reason. I raised an issue on the GitHub issue tracker for Smile, and the main author tells me that he will soon release a new version (2.1.2), and that this should work fine with Scala 2.11, 2.12 and 2.13.


