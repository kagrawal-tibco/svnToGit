import { registerLocaleData } from '@angular/common';
import localear from '@angular/common/locales/ar';
import localede from '@angular/common/locales/de';
import localeen from '@angular/common/locales/en';
import localees from '@angular/common/locales/es';
import localefr from '@angular/common/locales/fr';
import localeit from '@angular/common/locales/it';
import localeko from '@angular/common/locales/ko';
import localezh from '@angular/common/locales/zh';
import { enableProdMode, APP_INITIALIZER, LOCALE_ID, TRANSLATIONS, TRANSLATIONS_FORMAT } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

let locale = navigator.language as string;
if (locale.search('zh') === -1) {
  if (locale.indexOf('-') !== -1) {
    locale = locale.split('-')[0];
  }

  if (locale.indexOf('_') !== -1) {
    locale = locale.split('_')[0];
  }
}

// use the require method provided by webpack
declare const require;
// we use the webpack raw-loader to return the content as a string
const translations = require(`raw-loader!./locale/messages_${locale}.xlf`).default;

switch (locale) {
  case 'en': registerLocaleData(localeen, locale); break;
  case 'fr': registerLocaleData(localefr, locale); break;
  case 'it': registerLocaleData(localeit, locale); break;
  case 'de': registerLocaleData(localede, locale); break;
  case 'es': registerLocaleData(localees, locale); break;
  case 'ko': registerLocaleData(localeko, locale); break;
  case 'ar': registerLocaleData(localear, locale); break;
  case 'zh-CN': registerLocaleData(localezh, locale); break;
  case 'zh-HK': registerLocaleData(localezh, locale); break;
  case 'zh-TW': registerLocaleData(localezh, locale); break;
}

platformBrowserDynamic().bootstrapModule(AppModule, {
  providers: [
    { provide: TRANSLATIONS, useValue: translations },
    { provide: TRANSLATIONS_FORMAT, useValue: 'xlf' },
    { provide: LOCALE_ID, useValue: locale }

  ]
});
