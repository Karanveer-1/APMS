const assert = require('assert');

describe('APMS page', () => {
  it('should have the right title', () => {
    browser.url('http://phobos.edu.bcit.ca:8080/APMS');
    const title = browser.getTitle();
    assert.equal(title, 'Login');
  });

  it('should be able to login with valid credentials', () => {
    browser.url('http://phobos.edu.bcit.ca:8080/APMS');

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
