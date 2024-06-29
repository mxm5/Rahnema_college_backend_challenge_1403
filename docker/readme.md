# سوال داکر (۲۵۰ امتیاز)

ما در این چالش میخواهیم مهارت شما در یادگیری و استفاده از ابزارها رو بسنجیم، برای همین هم قرار است از ابزاری پرکاربرد به نام داکر استفاده کنید. اگر از قبل با داکر آشنایی ندارید اصلا نگران نباشید. میتوانید با خوندن [توضیحات](./course.md) و لینک‌هایی که براتون گذاشتیم و همچنین یه سری سرچ مختصر داکر رو مطالعه کنید، یاد بگیرید و بعد برگردید و سوالات رو حل کنید. بهتره که به این چالش به چشم فرصتی برای یادگیری نگاه کنید و نه یک آزمون ورودی :))

## قسمت اول

در قسمت اول این سوال ما یک ایمیج داکر برای شما آماده کرده‌ایم و از شما میخواهیم که :

1. داکر رو در سیستمتون نصب کنید.
2. ورژن `test` ایمیج زیر را پول کنید.

```
collegedev/interview_task
```

3. ایمیج رو در حالت اینترکتیو (Interactive mode) ران کنید. (اگه ایمیج رو به صورت عادی ران کنید کانتینرتون قبل از اینکه بتونید بهش ورودی بدین kill میشه)
4. اسمتون رو به عنوان ورودی به کانتینر بدین و پسوردی که ازش میگیرین رو یادداشت کنید
5. به عنوان خروجی این قسمت باید یه فایل به اسم [docker_p1.txt](./part_1/docker_p1.txt) بسازید و کامندهایی که در هر قسمت ران کردید، نامی که به برنامه دادید و پسوردی که از آن تحویل گرفتید را در این فایل بنویسید.</br>

**تذکر مهم: خیلی به هشدارهایی که میگیرید دقت کنید، شاید جایی اشتباه کرده باشید‌ :)**

## قسمت دوم

در این قسمت یک فایل [javascript](./part_2/index.js) در اختیارتون قرار داده شده. ازتون میخوایم یک داکرفایل بنویسید که ایمیجی بر پایه `node:alpine` بسازد که زمان اجرا شدن این فایل را اجرا میکند.





---
---
---

# getting a hash generated from your input using docker file

1. log in the Linux server that is having docker installed 
2. check the internet connection for the docker registry availability
```shell
ping google.com
```
3. pulling the image from the docker registry `collegedev/interview_task` 
```shell
docker pull collegedev/interview_task:test
```
4. wait for finishing the pulling of the docker image downloading
5. use the command to run the docker image interactive mode 
```sh
docker run -it collegedev/interview_task:test
```
6. this would be shown
```sh
Please enter your name: mohammad
Your password is 'U2FsdGVkX19rPaF3OVNj+zCeD0V6Yq6AAZi+ok2Skdw='
```


# create a docker image 

1. log in to server running Linux and having the docker installed in it 
2. create a desired directory 
```sh
mkdir docker-practice
```
3. move in the desired directory
```sh
cd docker-practice
```
4. create a docker file with this name `Dockerfile` that name is being used as default name when building docker images so you don't need to specify the file name when building docker images
```sh
touch Dockerfile
```
5. edit the `Dockerfile`
```sh
vi Dockerfile
```
6. before that ==copy the index.js== file in the same directory to easily introduce it to the docker file
7. add following in the docker image
```dockerfile
FROM node:lts-alpine

WORKDIR .

COPY ./index.js .

RUN chmod +x index.js

CMD ["node","index.js"]
```
7. now we should build the `dockerfile` using the command and with the -t option give it a custom tag like below command in the same directory that the docker file is present 
```sh
docker build -t node-alpine-test-mohammad .
```
8. check if the image is available in the system
```sh
docker image ls
```
9. then run the image using the following command 
```sh
docker run node-alpine-test-mohammad
```
10. and then in the terminal you will see this image 
```txt
If your seeing this message after running your container, then your Dockerfile is valid.
```





