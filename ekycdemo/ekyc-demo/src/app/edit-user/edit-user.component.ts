import { Component, OnInit , Inject} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {first} from "rxjs/operators";
import {ApiService} from "../core/api.service";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  user: User;
  editForm: FormGroup;
  constructor(private formBuilder: FormBuilder,private router: Router, private apiService: ApiService) { }

  ngOnInit() {
    const userId = window.localStorage.getItem("editUserId");
    if (!userId) {
      alert("Invalid action.")
      this.router.navigate(['list-user']);
      return;
    }
    this.editForm = this.formBuilder.group({
      id: [''],
      username: [{value: '', disabled: true}, Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      roleList: [[], Validators.required]
    });
    this.apiService.getUserById(+userId)
      .subscribe( data => {
        this.editForm.setValue(data.result);
        this.user = data.result;
        /*this.editForm.controls.id.setValue(data.result.id);
        this.editForm.controls.username.setValue(data.result.username);
        this.editForm.controls.firstName.setValue(data.result.firstName);
        this.editForm.controls.lastName.setValue(data.result.lastName);
        this.editForm.controls.roleList.setValue(data.result.roleList);*/
      });
  }

  onSubmit() {
    this.user.firstName = this.editForm.controls.firstName.value;
    this.user.lastName = this.editForm.controls.lastName.value;
    this.user.roleList = this.editForm.controls.roleList.value;

    this.apiService.updateUser(this.user)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            console.log('User updated successfully.');
            this.router.navigate(['list-user']);
          } else {
            alert(data.message);
          }
        },
        error => {
          alert(error);
        });
  }

  onCancel() {
    this.router.navigate(['list-user']);
  }
}
