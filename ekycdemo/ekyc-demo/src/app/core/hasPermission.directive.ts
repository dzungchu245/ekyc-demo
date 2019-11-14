import { Directive, ElementRef, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import {ApiService} from './api.service';
import {User} from '../model/user.model';

@Directive({
  // tslint:disable-next-line:directive-selector
  selector: '[hasPermission]'
})
export class HasPermissionDirective implements OnInit {
  private currentUser: User;
  private permissions = [];
  private logicalOp = 'OR';

  constructor(
    private element: ElementRef,
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private apiService: ApiService
  ) {
  }

  ngOnInit() {
    this.currentUser = this.apiService.getLoggedUser();
    this.updateView();
  }

  @Input()
  set hasPermission(val) {
    this.permissions = val;
    this.updateView();
  }

  @Input()
  set hasPermissionOp(permop) {
    this.logicalOp = permop;
    this.updateView();
  }

  private updateView() {
    if (this.checkPermission()) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

  private checkSinglePermission(checkRole: string) {
    const found = false;
    if (checkRole && typeof (checkRole) !== undefined) {
      const permission = this.currentUser.roleList.find(x => x === checkRole);
      if (permission) {
        return true;
      }
    }
    return found;
  }

  private checkPermission() {
    let hasPermission = false;
    if (this.currentUser) {
      for (const checkPermission of this.permissions) {
        const permissionFound = this.checkSinglePermission(checkPermission);
        if (permissionFound) {
          hasPermission = true;
          if (this.logicalOp === 'OR') {
            break;
          }
        } else {
          hasPermission = false;
          if (this.logicalOp === 'AND') {
            break;
          }
        }
      }
    }

    return hasPermission;
  }
}
