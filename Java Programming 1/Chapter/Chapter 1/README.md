## 프로그래밍 언어

- 기계어 : 이진수 명령어들로 구성된 언어
- 어셈블리어 : 기계어의 각 명령을 니모닉 기호로 일대일 대응시킨 언어
- 고급 언어 : 사람이 이해하기 쉽고 자료를 효율적으로 표현할 수 있는 언어

## 고급언어

- 절차 지향 언어(C) → 객체 지향 언어(C++,자바,C#)

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/81dae646-da67-4d5e-ae33-db7b5fc28ee5/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/81dae646-da67-4d5e-ae33-db7b5fc28ee5/Untitled.png)

참조 : [https://gbsb.tistory.com/3](https://gbsb.tistory.com/3)

## 절차 지향 언어

- 절차에서 진행되는 언어(프로그래밍을 실행 순서에 의해 코딩함)
- 빠르게 처리( 시간적 유리)
- 디버깅 어려움
- 순서가 바뀌면 힘듬

## 객체 지향 언어

- 객체(어떠한 데이터 및 실체)를 기반으로 하는 프로그래밍
- 캡슐화,상속,추상화,다형성 → 이후에 설명
- 코드 재활용성 높음
- 코딩이 간편함
- 속도 느림

## WORA(Write Once Run Anywhere)

- 자바는 플랫폼이 독립적이다 ( 운영체제나 하드웨어에 상관없이 JVM만 있으면 어디서나 동일한 실행 환경을 제공하며 결과값을 얻을 수 있다)
- 다시 컴파일 하거나 수정할 필요 없다.

## JVM(자바 가상 머신)

- 서로 다른 플랫폼에서 자바 프로그램이 실행되는 동일한 환경을 제공한다
- 필요한 클래스파일을 로딩하여 실행한다.(메모리 적음)

## 바이트 코드

- 자바 가상 머신에서만 실행되는 기계어 (어떤 cpu와도 관계 없는 바이너리 코드이다)
- 자바 컴파일러는 .java파일을 컴파일하여  바이트코드로 된 .class파일은 만든다
- JDK에는 자바 클래스 파일을 디어셈블하여 바이트코드로 볼수 있는 도구(javap)를 제공한다.

디어셈블이란?(disassemble)

기계어를 어셈블리어로 바꿔주는 과정이다.

## 자바 프로그램의 실행과정

- 여러 소스 파일(.java)로 나누어 작성 →컴파일 → 클래스 파일(.class)

    여기서 c/c++은 링크 과정을 통해 하나의 obj파일로 묶어서 보내지만 자바는 아니다.(용량 큼)

- JVM이 실행 도중 필요한 클래스파일을 로딩하고 실행해 준다.(시간 절약)

## JDK(Java Development Kit)

- 자바 응용프로그램을 개발하고 실행하는데 필요한 총체적 환경
- 응용 프로그램을 개발하는데 필요한 도구와 실행될 때 필요한 JVM과 표준 클래스를 포함하는 JRE(Java Run-time Environment)로 구성된다
- java SE(standard Edition) : 표준 배포판으로서 데스크톱과 서버 으용프로그램의 개발 환경
- java ME(Micro Edition) : 모바일용 배포판,제한된 리소스를 갖는 하드웨어의 개발환경
- java EE(Enterprise Edition) : 기업용 배포판( 다중사용자,대규모 기업)
- JDK는 자바 컴파일러,JRE,클래스 라이브러리, 자바 응용프로그램 소스 코드
- JRE는 자바 응용프로그램이 실행될 때 필요한 소프트웨어(JDK가 JRE를 포함)

## 자바 컴파일 과정

- Hello클래스를 만들었다면 Hello.java 로 저장한 후javac Hello.java를 하여 컴파일한다.
- 이후 Hello.class가 만들어지고 Hello.class를 실행하면 결과가 나온다


## 타입 변환

- 작은 타입 → 큰 타입 ( 자동 변환)    ex) long m = 25;
- 큰 타입 → 작은타입(강제 타입 변환) ex) int n = 30;  byte b = (byte)n;  -> 데이터 손실 발생!!
- 강제 타입 변환 : 캐스팅(casting)

## 키 입력(System.in)

- System.in 은 키보드 장치를 제어하고 키 입력을 받는 표준 **입력 스트림 객체**이다.
- 이는 입력받은 키를 단순한 바이트 정보로 전달하므로 변환해야하는 번거로움이 있다.
- 따라서 Scanner를 통해 원하는 타입으로 변환해주는 클래스를 사용한다.

## Scanner

- Scanner scanner =  new Scanner(System.in)  :  객체 생성
- java.util 패키지 안에 있다(import java.util.Scanner)
- Scanner는 공백으로 구분한다!(' ' , '\t' , '\n')
- 정수 입력 받기 :  int age  = scanner.nextInt()
- nextLine()은 한 줄이고 next()는 공백이 나올때 까지
- 마지막 scanner.close()

# 증감 연산

- ++a : a를 1 증가시키고 증가 전의 값 반환
- a++ : a를 1증가시키고 증가 된 값 반환

## 비트 연산

- 비트 논리 연산 : AND, OR, XOR, NOT
- 비트 시프트 연산 : 오른쪽이나 왼쪽으로 이동

### 비트 논리 연산

a&b : 모두 1이면 1

a | b : 모두 0이면 0

a^b : 다르면 1,같으면 0

~a : 1→0 , 0 →1

### 비트 시프트 연산

- a>>b : a의 각 비트를 오른쪽으로 b번 이동, 최상위 비트의 빈자리는 시프트 전의 최상위 비트로 채움
- a >>>b : 위와 같고 최상위 비트의 빈자리는 항상 0으로 채움
- a << b : a의 각 비트를 왼쪽으로 b번 쉬프트, 최하위 빈자리는 항상 0 으로 채움

## Switch 문

- switch문의 '식'을 먼저 계산하고 그 결과 값과 일치하는 case 문으로 분기한다. 만약 break를 만나면 벗어난다. default는 if문의 else와 같다.

```java
switch(식){
	case 값1:
			실행문장 1;
			break;
	case 값2:
			실행문장 2;
			break;
	deafault:
			실행문장 3;
}
```
