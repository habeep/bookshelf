package com.tansha.library.bookshelf.utils;

public class Constants {

	private Constants() {
    }

    /**
     * The Constant INSTAMOJO_API_ENDPOINT. Every API request is made to a
     * method on top of this base URL. HTTPS is mandatory.
     */
    public static final String INSTAMOJO_LIVE_API_ENDPOINT = "https://api.instamojo.com/v2/";

    public static final String INSTAMOJO_TEST_API_ENDPOINT = "https://test.instamojo.com/v2/";

    /**
     * The constant INSTAMOJO_AUTH_ENDPOINT.
     */
    public static final String INSTAMOJO_LIVE_AUTH_ENDPOINT = "https://www.instamojo.com/oauth2/token/";

    public static final String INSTAMOJO_TEST_AUTH_ENDPOINT = "https://test.instamojo.com/oauth2/token/";

    /**
     * The constant PARAM_CLIENT_ID.
     */
    public static final String PARAM_CLIENT_ID = "client_id";

    /**
     * The constant PARAM_CLIENT_SECRET.
     */
    public static final String PARAM_CLIENT_SECRET = "client_secret";

    /**
     * The constant PARAM_GRANT_TYPE.
     */
    public static final String PARAM_GRANT_TYPE = "grant_type";

    public static final String PARAM_USERNAME = "username";

    /**
     * The constant PARAM_GRANT_TYPE.
     */
    public static final String PARAM_REFRESH_TOKEN = "refresh_token";

    /**
     * The constant PARAM_PASSWORD.
     */
    public static final String PARAM_PASSWORD = "password";

    /**
     * The constant GRANT_TYPE_CLIENT_CREDENTIALS.
     */
    public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    /**
     * The constant GRANT_TYPE_PASSWORD.
     */
    public static final String GRANT_TYPE_PASSWORD = "password";

    /**
     * The constant GRANT_TYPE_REFRESH_TOKEN.
     */
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    /**
     * The constant HEADER_AUTHORIZATION.
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    public static final String HEADER_ACCEPT = "Accept";

    /**
     * The constant PATH_PAYMENT_ORDER.
     */
    public static final String PATH_PAYMENT_ORDER = "gateway/orders/";

    /**
     * The constant PATH_REFUND.
     */
    public static final String PATH_REFUND = "payments/";

    public static final String PATH_INVOICE = "invoices/";

    public static final String PATH_PAYOUT = "payouts/";

    public static final String PATH_PAYMENT_REQUEST = "payment_requests/";

    public static final String USERNAME = "im_testing";
    public static final String PASSWORD = "test@123";

    public static final String CLIENT_ID = "test_J5M3yYug3e8r2FXTNjZXoTAuHDNZiMjGuUS";
    public static final String CLIENT_SECRET = "test_2FWX8pILyNnQdaPRVXONZz7fIFoeUjbLabWaqDIqolG1qrnBGQCwdMwKzkLKNBzvuCVJih3j4bJpQoLhbpx0s64pUgSQFrUyjvVCyXN4R91AnEMzyroqhgdBOgQ";
    public static final String CLIENT_SALT = "d948a0334e164629a02ead3c0ca8e902";

}
