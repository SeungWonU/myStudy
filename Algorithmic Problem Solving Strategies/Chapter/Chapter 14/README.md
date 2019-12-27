# 14 정수론
## 14-1 도입
#### 이 장에서는 정수론에 대해 가장 기초적인 알고리즘들을 소개한다
## 14-2 소수
#### 소수(prime number)는 정수론의 가장 중요한 연구 대상 중 하나로, 양의 약수가 1과 자기 자신 두개 뿐인 자연수를 의미한다.
소수의 반대말로 세 개 이상의 약수를 갖는 합성수(composite number)가 있다.
> 코드 O(sqrt(n)) 시간에 작동하는 소수 판별 알고리즘
``` c++
// 주어진 자연수  n이 소수인지 확인한다.
bool isPrime(int n){
    // 예외 처리 : 1과 2는 예외로 처리한다.
    if(n <=1) return false;
    if(n==2) return true;
    // 2를 제외한 모든 짝수는 소수가 아니다.
    if(n%2 ==0) return false;
    //2를 제외했으니 3이상의 모든 홀수로 나누어보자.
    int sqrtn = int(sqrt(n));
    for(int div=3;div<sqrtn; div+=2)
        if(n% div ==0)
            return false;
}
```
### 에라토스테네스의 체
#### 2부터 n까지 수를 나열하여 지워지지 않는 수의 배수들을 지워나가는 방법이다. sqrt보다 배수를 지워 나가는 방법이 더 빠르다.
>코드 : 에라토스테네스의 체를 이용한 소인수 분해
``` c++
//minFactor[i] =i의 가장 작은 소인수(i 가 소수인 경우 자기 자신)
int minFactor[MAX_N];
//에라토스테네스의 체를 수행하면서 소인수분해를 위한 정보도 저장한다.
void eratosthenes2(){
//1은 항상 에외 처리
minFactor[0] = minFactor[1] = -1;
//모든 숫자를 처음에는 소수로 표시해 둔다.
for(int i=2;i<n;i++)
    minFactor[i]=i;
    //에라토스테네스의 체를 수행한다.
    int sqrtn = int(sqrt(n));
for(int i=2;i<sqrtn;i++)
{
    if(minFactor[i]==i){
        for(int j= i*i; j<=n; +=i)
            if(minFactor[j]==i)
                minFactor[j]=i;
    }
}
}
vector<int> factor(int n) {
vector<int> ret;
while(n>1){
    ret.push_back(minFactor[n])
    n /= minFactor[n];
}
return ret;
}
```
