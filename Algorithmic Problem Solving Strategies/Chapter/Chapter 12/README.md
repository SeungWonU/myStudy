# 12 최적화 문제를 결정 문제로 바꿔 풀기
## 12-1 도입
#### 최적화 문제를 결정 문제(decision problem)으로 바꾼 뒤, 이것을 이분법을 이용해 해결하는 방법이 있다. 결정 문제란 예 혹은 아니오 형태의 답만이 나오는 문제들을 가리킨다. 최적화 문제의 반환 값은 대개 실수나 정수이므로 답의 경우의 수가 무한한데, 결정 문제는 두 가지 답만이 있을 수 있다.
외판원 문제로 둘을 표현하였다.
1. optimize(G) = 그래프 G의 모든 정점을 한 번씩 방문하고 시작점으로 돌아오는 최단 경로의 길이를 반환한다.(최적화 문제)
2. decision(G,x) = 그래프 G의 모든 정점을 한 번씩 방문하고 시작점으로 돌아오면서 길이가 x 이하인 경로의 존재 여부를 반환.(결정 문제)
### 예제 : DARPA Grand Challenge(난이도 : 중)
#### 이 문제는 운전자가 없는 차들을 컴퓨터 인공지능으로 조작해 누가 먼저 결승점에 도달하느냐를 겨루는 인공지능 대회이다. m군데의 카메라 설치할 수 있는 곳 중에 n개의 카메라를 설치할 때, 가장 가까운 두 카메라 사이의 간격을 최대화하자.
#### 최적화 결정 문제 : 이 문제를 함수 형태로 표현해 보자.
> optimize(locations,cameras) = 카메라를 설치할 수 있는 위치 locations와 카메라의 수 cameras가 주어질 떄, 카메라 간 최소 간격의 최대치를 반환한다.
#### 이것은 최적화 문제이다. 이 문제를 결정 문제로 변환해 보자.
>decision(locations,cameras,gap) = 이들을 적절히 배치해 모든 카메라의 간격이 gap 이상이 되도록 하는 방법이 있는가?
#### 탐욕법으로 결정 문제를 해결하는 decision()과 이를 사용하는 optimize()를 구현해 보자.
``` c++
// 결정 문제 : 정렬되어 있는 locations 중 cameras를 선택해 모든 카메라의 간격이 gap 이상인 방법을 반환
bool decision(const vector <double>& location, int cameras, double gap) {
// 카메라를 설치할 수 있을 때마다 설치하는 탐욕적 알고리즘
double limit = -1;
int installed = 0;
for(int =0;i< location.size();i++) {
    if(limit <= location[i]) {
    ++installed;
    //location[i]+gap 이후는 되어야 카메라를 설치할 수 있다.
    limit = locaiton[i] +gap;
    }
    }
    // 결과적으로 cameras대 이상을 설치할 수 있었으면 성공
    return installed >= cameras;
}
// 최적화 문제 : 정렬되어 있는 locations 중 cameras를 선택해 최소 간격을 최대화한다.
double optimize(const vector<double>& lcoation,int cameras) {
    double lo = 0, hi =241;
    //반복문 불변식 : decision(lo) && !decision(hi)
    for(int it=0;it<100;it++) {
    double mid =( lo+ hi)/ 2.0;
    // 간격이 mid 이상이 되도록 할 수 없음녀 답은 [lo,hi]에 있다.
    if(decision(location,cameras,mid))
        lo=mid;
     else
        hi=mid;
    }
    return lo;
}
```
### 최적화 문제를 결정 문제로 바꾸는 레시피
* #### 가장 좋은 답은 무엇인가 라는 최적화 문제를 x혹은 그보다 좋은 답이 있는가? 라는 결정 문제로 바꾼다.
* #### 결정 문제를 쉽게 풀 수 있는 방법이 있는지 찾아본다.
* #### 결정 문제를 내부적으로 이용하는 이분법 알고리즘을 작성한다.
## 12-2 남극기지(난이도 : 하)
#### 남극에는 n개의 탐사 기지가 있다.

