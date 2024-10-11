@Component
public class LanguageUtils {

    /**
     * <p>Field ms: Message Source</p>
     */
    private static MessageSource ms;

    /**
     * <p>Description: init message source</p>
     * @param source MessageSource
     */
    @Autowired
    public void init(MessageSource source) {
        LanguageUtils.ms = source;
    }

    /**
     * <p>Description: Constructor</p>
     */
    private LanguageUtils() {
    }

    /**
     * <p>Description: change language</p>
     * @param request HttpServletRequest
     * @param language language
     */
    public static void changeLanguage(HttpServletRequest request, String language) {
        if (null != request) {
        }
    }

    /**
     * <p>Description: get message sourc</p>
     * @param code CODE
     * @return data
     */
    public static String getMessage(String code) {
        return ms.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * <p>Description: get message sourc</p>
     * @param code CODE
     * @param args parameters
     * @return data
     */
    public static String getMessage(String code, String... args) {
        return ms.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * <p>Description: get message source</p>
     * @param request HttpServletRequest
     * @param code CODE
     * @return data
     */
    public static String getMessage(HttpServletRequest request, String code) {
        WebApplicationContext wac = RequestContextUtils.findWebApplicationContext(request);
        return wac.getMessage(code, new Object[] {}, "", (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
    }

    /**
     * <p>Description: get message source</p>
     * @param request HttpServletRequest
     * @param code CODE
     * @param deft default
     * @return data
     */
    public static String getMessage(HttpServletRequest request, String code, String deft) {
        WebApplicationContext wac = RequestContextUtils.findWebApplicationContext(request);
        return wac.getMessage(code, new Object[] {}, deft, (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
    }

    /**
     * <p>Description: get message source</p>
     * @param request HttpServletRequest
     * @param code CODE
     * @param locale locale
     * @return data
     */
    public static String getMessage(HttpServletRequest request, String code, Locale locale) {
        WebApplicationContext wac = RequestContextUtils.findWebApplicationContext(request);
        return wac.getMessage(code, new Object[] {}, "", locale);
    }

    /**
     * <p>Description: get message source</p>
     * @param request HttpServletRequest
     * @param code CODE
     * @param deft default
     * @param locale locale
     * @return data
     */
    public static String getMessage(HttpServletRequest request, String code, String deft, Locale locale) {
        WebApplicationContext wac = RequestContextUtils.findWebApplicationContext(request);
        return wac.getMessage(code, new Object[] {}, deft, locale);
    }

}
