const assert = require('assert');

describe('APMS page', () => {
  it('should have the right title', () => {
    browser.url('http://phobos.edu.bcit.ca:8080/APMS');
    const title = browser.getTitle();
    assert.equal(
      title,
      'Login'
    );
  });
});
