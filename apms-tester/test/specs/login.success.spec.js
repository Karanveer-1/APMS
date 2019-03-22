const assert = require('assert');

describe('APMS page', () => {
  it('should be able to login with valid credentials', () => {
    browser.url('http://phobos.edu.bcit.ca:8080/APMS');

    const username = $('#loginGrid\\:usernameInput');
    username.setValue('amy');
    const password = $('#loginGrid\\:passwordInput');
    password.setValue('amy');

    const loginButton = $('.btn.btn-primary.btn-block');
    loginButton.click();

    browser.pause(3000);
    const title = browser.getTitle();
    assert.equal(title, 'Dashboard');
  });
});
