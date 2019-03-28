const assert = require('assert');

const BASE_URL = 'http://localhost:8080/APMS';

describe('APMS page', () => {
  it('should have the right title', () => {
    browser.url(BASE_URL);
    const title = browser.getTitle();
    assert.equal(title, 'Login');
  });

  it('should be able to login with valid credentials', () => {
    browser.url(BASE_URL);

    const username = $('#loginGrid\\:usernameInput');
    username.setValue('admin');
    const password = $('#loginGrid\\:passwordInput');
    password.setValue('password');

    const loginButton = $('.btn.btn-primary.btn-block');
    loginButton.click();

    browser.pause(3000);
    const title = browser.getTitle();
    assert.equal(title, 'Dashboard');
  });
});
