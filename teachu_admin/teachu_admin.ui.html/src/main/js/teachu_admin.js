import {RemoteApp} from '@eclipse-scout/core';
import * as teachu_admin from './index';

Object.assign({}, teachu_admin); // Use import so that it is not marked as unused

new RemoteApp().init();
