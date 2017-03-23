package com.ps.tutorial.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * Spring MVC Architecture:
 *
 * Request ->
 *   -> ServletDispatcher ->
 *     NOTE: On startup ServletDispatcher autowires all available HandlerMappings, MappingHandlerAdapter, ViewResolvers.
 *     By default there is RequestMappingHandlerMapping, BeanNameUrlHandlerMapping, SimpleUrlHandlerMapping mappers (plus adapters)
 *     and InternalResourceViewResolver configured
 *     1. Get HandlerMapping for Request (NOTE: registered handlers have priority), types:
 *       - DefaultAnnotationHandlerMapping (maps requests to all existing RequestMapping, not recommended)
 *       - BeanNameUrlHandlerMapping (DEFAULT, maps URL to a controller bean name directly, not recommended)
 *       - ControllerBeanNameHandlerMapping (similar to BeanNameUrlHandlerMapping, not recommended)
 *       - ControllerClassNameHandlerMapping (maps all URLs to controller class name, not recommended)
 *       - SimpleUrlHandlerMapping (maps the list of specified URLs to controller name, not recommended)
 *       - RequestMappingHandlerMapping (DEFAULT, maps requests to all existing @Controller/@RequestMapping combination, actual)
 *         It provides HandlerMethod as a handler mapping for a particular request
 *         Supports the following method arguments (argument types are resolved by HandlerMethodArgumentResolver):
 *           - @RequestParam Map (key-value request query params, by RequestParamMapMethodArgumentResolver)
 *           - @RequestParam("name") Primitive (gets request param by name, by RequestParamMethodArgumentResolve)
 *           - @PathVariable Map (URI template key-values, by PathVariableMapMethodArgumentResolver)
 *           - @PathVariable("name") Primitive (gets path variable by URL template name, by ...)
 *           - @MatrixVariable Map (key-value matrix query params, by ...)
 *           - @MatrixVariable("name") Primitive (matrix query param by name, by ...)
 *           - @RequestHeader Map (key-value request headers, by RequestHeaderMapMethodArgumentResolver)
 *           - @RequestHeader("name") Primitive (gets request header value by name, by RequestHeader )
 *           - Model (model object, by ModelMethodProcessor)
 *           - ModelAttribute("name") (maps model.asMap().get("name"), by ModelAttributeMethodProcessor)
 *           - Map (by MapMethodProcessor)
 *           - Errors (result of bean validation, by ErrorsMethodArgumentResolver)
 *           - CookieValue("name") (gets cookie value by name, by AbstractCookieValueMethodArgumentResolver)
 *           - UriComponentsBuilder (URI builder, UriComponentsBuilderMethodArgumentResolver)
 *           - OutputStream | Writer (response output stream, by ServletResponseMethodArgumentResolver)
 *           - WebRequest | ServletRequest | MultipartRequest | HttpSession | Principal | Locale | InputStream | Reader (by ServletRequestMethodArgumentResolver)
 *           - RedirectAttributes (attributes to resolve redirect template, by RedirectAttributesMethodArgumentResolver)
 *     2. Get appropriate MappingHandlerAdapter, there is an adapter per each MappingHandler. We need it to execute MappingHandler
 *     3. Handle request: adapter.handle(request, response, mappingHandler) -> ModelAndView
 *        EXAMPLE for HandlerMethod (RequestMappingHandlerMapping).
 *            HandlerMethod return value (method + value). It can populate ModelAndView or directly write to Response.
 *            Controller's mapped method processes the request and returns a value (Object, String, ModelAndView)
 *            By default, the following 13 HandlerMethodReturnValueHandler are supported:
 *             - Object with @ResponseBody (by RequestResponseBodyMethodProcessor).
 *               It takes the returned object, converts it and write to Response output.
 *               RequestResponseBodyMethodProcessor supports a list of media types and have a list of MessageConverters.
 *               By default it has 7 converters:
 *                 - *//*, application/octet-stream (by ByteArrayHttpMessageConverter)
 *                 - *//*, text/plain;charset=ISO-8859-1 (by StringHttpMessageConverter)
 *                 - *//* (by ResourceHttpMethodConverter)
 *                 - application/xml, application/*+xml, text/xml (by SourceHttpMethodConverter)
 *                 - application/xml, application/*+xml, text/xml (by Jaxb2RootElementHttpMessageConverter)
 *                 - application/x-www-form-urlencoded, multipart/form-data (by AllEncomprassingFormHttpMessageConverter)
 *                 - application/json;charset;=UTF-8, application/*+json;charset;=UTF-8 (by MappingJackson2HttpMessageConverter)
 *               Plus it contains ContentNegotiationManager which can map file extensions to MediaTypes, etc.
 *             - HttpEntity | RequestEntity (by HttpEntityMethodProcessor)
 *             - String | Void (by ViewNameMethodReturnValueHandler)
 *             - ModelAndView (by ModelAndViewMethodReturnValueHandler)
 *             - Model (by ModelMethodProcessor)
 *             - View (by ViewMethodReturnValueHandler)
 *             - Map (by MapMethodProcessor)
 *             - With @ModelAttribute (by ModelAttributeMethodProcessor)
 *             - HttpHeaders (by HttpHeadersReturnValueHandler)
 *             - Callable (by CallableMethodReturnValueHandler)
 *             - DeferredResult (by DeferredResultMethodReturnValueHandler)
 *             - WebAsyncTask (by AsyncTaskMethodReturnValueHandler)
 *             - ListenableFuture (by ListenableFutureReturnValueHandler)
 *       ModelAndView result (logical name of view + model) or NULL if response body is already written ->
 *     4. If ModelAndView is not NULL, Dispatcher use ViewResolver to create View, otherwise just return Response
 * If ModelAndView is not NULL calls view.render(model) and write to Response, otherwise just return Response
 *
 * NOTE: SimpleUrlHandlerMapping may contain ResourceHttpRequestHandler which serve static resources.
 * This handler gets mapped by HttpRequestHandlerAdapter. ResourceHttpRequestHandler reads a resource and writes to
 * output stream 
 * 
 * View Resolvers:
 * - AbstractCachingViewResolver - abstract view resolver that caches views. 
 *   Often views need preparation before they can be used; extending this view resolver provides caching.
 * - XmlViewResolver (LEGACY) - implementation of ViewResolver that accepts a configuration file written in XML with the same DTD 
 *   as Spring’s XML bean factories. The default configuration file is /WEB-INF/views.xml 
 * - ResourceBundleViewResolver (LEGACY) - implementation of ViewResolver that uses bean definitions in a ResourceBundle, 
 *   specified by the bundle base name. Typically you define the bundle in a properties file, located in the classpath. 
 *   The default file name is views.properties.
 * - UrlBasedViewResolver - simple implementation of the ViewResolver interface that effects the direct resolution of logical 
 *   view names to URLs, without an explicit mapping definition. This is appropriate if your logical names match the names 
 *   of your view resources in a straightforward manner, without the need for arbitrary mappings.
 * - InternalResourceViewResolver (IMPORTANT) - convenient subclass of UrlBasedViewResolver that supports InternalResourceView 
 *   (in effect, Servlets and JSPs) and subclasses such as JstlView and TilesView. You can specify the view class 
 *   for all views generated by this resolver by using setViewClass(..).
 * - VelocityViewResolver/FreeMarkerViewResolver - Convenient subclass of UrlBasedViewResolver that supports VelocityView or FreeMarkerView.
 * - ContentNegotiatingViewResolver (IMPORTANT) - Implementation of the ViewResolver interface that resolves a view based on the request file name or Accept header.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.ps.tutorial.rest.controllers"})
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Configure custom content negotiation strategy (for json, xml and so on)
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //Json already enabled by default
    }

}
