import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { Router } from '@angular/router';
import { PublicationService } from 'src/app/shared/services/publication.service';


@Component({
  selector: 'app-add-blog',
  templateUrl: './add-blog.component.html',
  styleUrls: ['./add-blog.component.css']
})
export class AddBlogComponent implements OnInit, OnDestroy {

  reactiveForm: FormGroup

  constructor(private router: Router, private blogService: PublicationService) { }
  
  ngOnDestroy(): void {
    this.blogService.ngOnDestroy()
  }

  ngOnInit() {
    this.reactiveForm = new FormGroup({
      title: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      author: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      content: new FormControl(null, [Validators.required, Validators.minLength(10)])

    })
  }

  onSubmit() {
    const blogInputs = this.reactiveForm.value;
    this.blogService.createPublication(blogInputs).subscribe({
      next: res => {
        if (res) {
          this.router.navigate(['/'])
        }
      },
      error: err => {
        return
      },

    })
  }

}
