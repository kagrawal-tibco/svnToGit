import { TibcoAmsPage } from './app.po';
import { browser, element, by } from 'protractor';

/**
 * This test suite expects an empty database with user admin/admin
 */
describe('tibco-ams is capable of doing basic log in and log out', function () {
  let page: TibcoAmsPage;

  beforeAll(() => {
    page = new TibcoAmsPage();
  });

  beforeEach(() => {
    // this only works well for angular 1, so we disable it
    browser.ignoreSynchronization = true;
  });

  it('should display login page', () => {
    // for the very first page loading, this is still effective
    browser.ignoreSynchronization = false;
    page.navigateTo();
    expect(page.getHeaderText()).toEqual('Sign In');
  });

  it('should be able to login', () => {
    page.login();
    browser.sleep(1000);
    expect(element(by.tagName('welcome'))).toBeTruthy('not seeing welcome');
    expect(browser.getCurrentUrl()).toContain('workspace');
  });

  it('shall be able to toggle user menu', () => {
    let odd = Math.floor(Math.random() * 10) * 2 + 1;
    for (let i = 0; i < odd; i++) {
      page.clickUsermenuToggle();
      browser.sleep(100);
    }

    expect(element(by.id('user-menu-toggle')).getAttribute('class')).toContain('active', 'fail the toggle check');
    expect(element(by.id('user-menu-header'))).toBeTruthy('shall be able to see usermenu');
  });

  it('shall be able to logout', () => {
    page.logout();
    browser.sleep(1000);
    expect(page.getHeaderText()).toEqual('Sign In');
  });
});
