import { browser, element, by } from 'protractor';

export class TibcoAmsPage {
  navigateTo() {
    return browser.get('/');
  }

  getHeaderText() {
    return element(by.css('h3')).getText();
  }

  login() {
    element(by.css('#username')).sendKeys('admin');
    element(by.css('#password')).sendKeys('admin');
    element(by.css('.submit-button')).click();
  }

  clickUsermenuToggle() {
    return element(by.id('user-menu-toggle')).click()
      .then(() => { }, err => {
        console.log('unable to toggle the user menu because: ', err);
        throw err;
      });
  }

  logout() {
    element(by.id('logout')).click();
  }
}
