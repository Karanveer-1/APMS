const assert = require('assert');

describe('Dashboard', () => {
  it('should be able to login with valid credentials', () => {
    browser.url('http://phobos.edu.bcit.ca:8080/APMS');

    const username = $('#loginGrid\\:usernameInput');
    username.setValue('andy');
    const password = $('#loginGrid\\:passwordInput');
    password.setValue('andy');

    const loginButton = $('.btn.btn-primary.btn-block');
    loginButton.click();

    browser.pause(3000);
    const title = browser.getTitle();
    assert.equal(title, 'Dashboard');
  });

  it('should have the right title', () => {
    browser.url('http://phobos.edu.bcit.ca:8080/APMS/faces/Dashboard.xhtml');

    const title = browser.getTitle();
    assert.equal(title, 'Dashboard');

    const pageTitle = $('.navbar-brand').getText();
    assert.equal(pageTitle, 'Dashboard');
  });
});
