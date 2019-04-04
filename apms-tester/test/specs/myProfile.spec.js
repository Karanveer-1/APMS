const assert = require('assert');

const TITLE = 'My Profile';
const BASE_URL = 'http://localhost:8080/APMS';

describe('My Profile', () => {
  it(`should be able to login with valid credentials`, () => {
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

  it(`should be able to access the page by clicking button on navigation bar`, () => {
    const navButton = $('#j_idt8\\:nav-my-profile');
    navButton.click();

    browser.pause(3000);
    const title = browser.getTitle();
    assert.equal(title, TITLE);
  });

  it(`should have the right title`, () => {
    const title = browser.getTitle();
    assert.equal(title, TITLE);

    const pageTitle = $('.navbar-brand').getText();
    assert.equal(pageTitle, TITLE);
  });

  it(`should display the employee's information`, () => {
    const table = $('#grid');
    // todo
  });

  it(`should have a 'Generate Key Pairs' button`, () => {
    const button = $('#configure-signature');
    // todo
  });
});
