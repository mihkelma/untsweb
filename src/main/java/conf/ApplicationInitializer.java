package conf;

import dao.SetupDao;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {

        new SetupDao().createSchema();
        super.onStartup(servletContext);
        // Schema creation is done here
    }

    @Override
    protected Class<?>[] getRootConfigClasses() { return new Class[] {}; }

    @Override
    protected Class<?>[] getServletConfigClasses() { return new Class[] { SpringConfig.class }; }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/*" }; }
}
