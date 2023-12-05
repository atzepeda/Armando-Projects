import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiService } from './api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  data: any;

  title = 'grappling-UI';
  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    console.log("Just an update to know we're working")
    // this.http.get('http://127.0.0.1:5001/v1/movies/0').subscribe(
    //   (response) => {
    //     this.data = response;
    //     console.log(this.data);
    //   },
    //   (error) => {
    //     console.log(error);
    //   }
    // );
  }

  onButtonClick() {
    this.apiService.fetchData().subscribe((result) => {
      this.data = result;
      console.log(this.data)
      console.log(this.data.id)
    });
  }
}
