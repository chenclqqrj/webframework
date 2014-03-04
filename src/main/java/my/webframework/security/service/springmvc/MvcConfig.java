package my.webframework.security.service.springmvc;

import java.util.List;

import my.webframework.security.service.springmvc.helper.Utf8StringHttpMessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "my.webframework.security.service.springmvc" })
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 例子
		// registry.addResourceHandler("/assets/**")
		// .addResourceLocations("classpath:/META-INF/resources/webjars/")
		// .setCachePeriod(31556926);
		// registry.addResourceHandler("/css/**").addResourceLocations("/css/")
		// .setCachePeriod(31556926);
		// registry.addResourceHandler("/img/**").addResourceLocations("/img/")
		// .setCachePeriod(31556926);
		// registry.addResourceHandler("/js/**").addResourceLocations("/js/")
		// .setCachePeriod(31556926);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(new Utf8StringHttpMessageConverter());
		MappingJackson2HttpMessageConverter jmc = new MappingJackson2HttpMessageConverter();
		// 此处解决OSIV方案中，使用了OpenEntityManagerSessionInViewFilter 在使用Jackson序列化为JSON的过程中，依然会出现no-session
		converters.add(jmc);
		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(new SourceHttpMessageConverter());
		converters.add(new AllEncompassingFormHttpMessageConverter());
	}
// 好像是比较牛B的配置
//	@Override
//	public void configureContentNegotiation(
//			ContentNegotiationConfigurer configurer) {
//		configurer.favorPathExtension(true).useJaf(false).favorParameter(true)
//				.parameterName("mediaType").ignoreAcceptHeader(true)
//				.defaultContentType(MediaType.APPLICATION_JSON)
//				.mediaType("xml", MediaType.APPLICATION_XML)
//				.mediaType("json", MediaType.APPLICATION_JSON);
//	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new SessionHandlerInterceptor());
	}

    @Bean  
    public ViewResolver getViewResolver() {  
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();  
        resolver.setViewClass(FreeMarkerView.class);
        resolver.setCache(false);  
//      resolver.setPrefix("");  
        resolver.setContentType("text/html; charset=utf-8");
        resolver.setSuffix(".ftl");  
        return resolver;  
          
    }  
  @Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		TemplateLoader tl = new ClassTemplateLoader(MvcConfig.class,
				"/my/webframework/ui");
		MultiTemplateLoader mtl = new MultiTemplateLoader(
				new TemplateLoader[] { tl });
		configurer.getConfiguration().setTemplateLoader(mtl);
		return configurer;
	}

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10000000);
        return multipartResolver;
    }
}
