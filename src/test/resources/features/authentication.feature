Feature: Test the correct functionality of the authentication

  Scenario: client makes call GET /wallet without authentication. Request should be denied.
    When the client calls GET "/wallet"
    Then the client receives status code of 403

  Scenario: client makes call GET /wallet/0 without authentication. Request should be denied.
    When the client calls GET "/wallet/0"
    Then the client receives status code of 403

  Scenario: client makes call POST /wallet without authentication. Request should be denied.
    When the client calls POST "/wallet" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call PUT /wallet/0 without authentication. Request should be denied.
    When the client calls PUT "/wallet/0" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call DELETE /wallet/0 without authentication. Request should be denied.
    When the client calls DELETE "/wallet/0" with body "{}"
    Then the client receives status code of 403


  Scenario: client makes call GET /income without authentication. Request should be denied.
    When the client calls GET "/income"
    Then the client receives status code of 403

  Scenario: client makes call GET /income/0 without authentication. Request should be denied.
    When the client calls GET "/income/0"
    Then the client receives status code of 403

  Scenario: client makes call POST /income without authentication. Request should be denied.
    When the client calls POST "/income" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call PUT /income/0 without authentication. Request should be denied.
    When the client calls PUT "/income/0" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call DELETE /income/0 without authentication. Request should be denied.
    When the client calls DELETE "/income/0" with body "{}"
    Then the client receives status code of 403


  Scenario: client makes call GET /outcome without authentication. Request should be denied.
    When the client calls GET "/outcome"
    Then the client receives status code of 403

  Scenario: client makes call GET /outcome/0 without authentication. Request should be denied.
    When the client calls GET "/outcome/0"
    Then the client receives status code of 403

  Scenario: client makes call POST /outcome without authentication. Request should be denied.
    When the client calls POST "/outcome" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call PUT /outcome/0 without authentication. Request should be denied.
    When the client calls PUT "/outcome/0" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call DELETE /outcome/0 without authentication. Request should be denied.
    When the client calls DELETE "/outcome/0" with body "{}"
    Then the client receives status code of 403


  Scenario: client makes call GET /category without authentication. Request should be denied.
    When the client calls GET "/category"
    Then the client receives status code of 403

  Scenario: client makes call GET /category/0 without authentication. Request should be denied.
    When the client calls GET "/category/0"
    Then the client receives status code of 403

  Scenario: client makes call POST /category without authentication. Request should be denied.
    When the client calls POST "/category" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call PUT /category/0 without authentication. Request should be denied.
    When the client calls PUT "/category/0" with body "{}"
    Then the client receives status code of 403

  Scenario: client makes call DELETE /category/0 without authentication. Request should be denied.
    When the client calls DELETE "/category/0" with body "{}"
    Then the client receives status code of 403


  Scenario: client makes call GET /balance/0 without authentication. Request should be denied.
    When the client calls GET "/balance/0"
    Then the client receives status code of 403

  Scenario: client makes call GET /outcome/0/2014-12-03T10:15:30.00Z/2014-12-03T10:15:30.00Z without authentication. Request should be denied.
    When the client calls GET "/outcome/0/2014-12-03T10:15:30.00Z/2014-12-03T10:15:30.00Z"
    Then the client receives status code of 403

  Scenario: client makes call GET /income/0/2014-12-03T10:15:30.00Z/2014-12-03T10:15:30.00Z without authentication. Request should be denied.
    When the client calls GET "/income/0/2014-12-03T10:15:30.00Z/2014-12-03T10:15:30.00Z"
    Then the client receives status code of 403

  Scenario: client makes call GET /net-income/0/2014-12-03T10:15:30.00Z/2014-12-03T10:15:30.00Z without authentication. Request should be denied.
    When the client calls GET "/net-income/0/2014-12-03T10:15:30.00Z/2014-12-03T10:15:30.00Z"
    Then the client receives status code of 403

  Scenario: client makes call GET /income/mean/0/2022-01/2022-02 without authentication. Request should be denied.
    When the client calls GET "/income/mean/0/2022-01/2022-02"
    Then the client receives status code of 403

  Scenario: client makes call GET /outcome/mean/0/2022-01/2022-02 without authentication. Request should be denied.
    When the client calls GET "/outcome/mean/0/2022-01/2022-02"
    Then the client receives status code of 403

  Scenario: client makes call GET /categories/mean/0/2022-01/2022-02 without authentication. Request should be denied.
    When the client calls GET "/categories/mean/0/2022-01/2022-02"
    Then the client receives status code of 403

  Scenario: client makes call GET /categories/mean/0/2022-01/2022-02 without authentication. Request should be denied.
    When the client calls GET "/categories/mean/0/2022-01/2022-02"
    Then the client receives status code of 403