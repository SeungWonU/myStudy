# 06 무식하기 풀기
## 6-1 도입
&nbsp; 완전 탐색(exhaustive search)이란 가능한 방법을 전부 만들어 보는 알고리즘을 뜻한다.무식하게 푸는 것(brute-forec)이 때론 빠른 알고리즘의 기반이 될 수 있다.

## 6-2 재귀 호출과 완전 탐색
### 재귀 호출
&nbsp; 재귀 함수(recursive function)란 자신이 수행할 작업을 유사한 형태의 여러 조각으로 쪼갠 뒤 그 중 한 조각을 수행하고, 나머지를 자기 자신을 호출해 실행하는 함수를 가르킨다.
>  1부터 n 까지의 합을 계산하는 반복 함수와 재귀 함수
```c++
// 1~n 까지의 합을 반환한다.
int sum(int n){
int ret = 0;
for(int i=1;i<=n;++i)
ret+= i;
return ret; }

int recursiveSum(int n){
if(n==1) return 1; // 더이상 쪼개지지 않을 때(기저 사례)
return n + recursiveSum(n-1); }
```
&nbsp; 재귀 함수는 '더이상 쪼개지지 않는' 최소한의 작업에 도달했을 때 답을 곧장 반환하는 조건문을 포함해야 한다. 이러한 쪼개지지 않는 가장 작은 작업들을 가리켜 기저 사례(base case)라고 한다. 기저 사례는 또한 재귀 호출에 반드시 존재해야 한다.
### 예제 : 중첩 반복문 대체하기
&nbsp; 0번부터 차례대로 번호 매겨진 n개의 원소 중 4개를 고르는 모든 경우를 출력하는 코드를 작성해 보자. 물론 이것을 4중 for 문을 사용하여 풀 수도 있다. 하지만 4개가 아니라 수가 100개가 된다면 우리는 for 문을 100번 중첩해야 한다. 재귀 호출은 이렇게 코드가 길어지거나 복잡해지는 것을 방지하기 위해 단순하고 간결한 코드로 작성된다. 아래 코드는 이 작업을 하는 재귀 함수를 보여준다.
``` c++
// n : 전체 원소의 수   picked : 지금까지 고른 원소들의 번호   toPick : 더 고를 원소의 수
// 앞으로 toPick 개의 원소를 고르는 모든 방법을 출력한다.
void pick(int n, vector<int>& picked, int toPick) {
    // 기저 사례 : 더 고를 원소가 없을 때 고른 원소들을 출력한다.
    if(toPick == 0) {printPicked(picked); return;}
    // 고를 수 있는 가장 작은 번호를 계산한다.
    int smallest = picked.empty() ? 0 : picked.back() + 1;
    // 이 단계에서 원소 하나를 고른다.
    for(int next = smallest; next<n; ++next){
        picked.push_back(next);
        pick(n,picked,toPick-1);
        picked.pop_back();
    }
}
```
### 예제 : 보글 게임(난이도 : 하)
&nbsp; 보글은 5x5 크기의 알파벳 격자를 가지고 하는 게임이다. 이때 게임의 목적은 상하좌우/대각선으로 인접한 칸들의 글자들을 이어서 단어를 찾아내는 것이다. 예를 들어 PRETTY,GIRL 등의 단어를 5x5 크기의 격자 안에서 찾을 수 있다. 각 글자들은 대각선으로도 이어질 수 있으며, 한 글자가 두 번 이상 찾을 수도 있다.
> hasWord(y,x,word)=보글 게임판의 (y,x)에서 시작하는 단어 word 의 존재 여부를 반환한다.
``` c++
// 보글 게임 구현
const int dx[8] = { -1, -1, -1, 1, 1, 1, 0, 0 };
const int dy[8] = { -1,  0,  1,-1, 0, 1,-1, 1 };
// 5x5 보글 게임 판의 해당 위치에서 주어진 단어가 시작하는지를 판단
bool hasWord(int y, int x, const string& word) {
    // 기저 사례 1 : 시작 위치가 범위 밖이면 무조건 실패
    if(!inRange(y,x)) return false;
    // 기저 사례 2 : 첫 글자가 일치하지 않으면 실패
   if(board[y][x] != word[0]) return false;
   //기저 사례 3 : 단어 길이가 1이면 성공
   if(word.size() == 1) return true;
   //인접한 여덟 칸을 검사한다.
   for(int direction = 0; direction<8; ++direction){
    int nextY = y + dy[direction] , nextX = x + dx[direction];
    // 다음칸이 범위 안에 있는지, 첫 글자는 일치하는지 확인할 필요가 없다.
    if(hasWord(nextY,nextX,word.substr(1)))
        return true;
   }
   return false;
}
``` 
### 완전 탐색 레시피
* 어떤 문제를 완전 탐색으로 해결하기 위해 필요한 과정은 다음과 같다.
1.완전 탐색은 존재하는 모든 답을 하나씩 검사하므로, 걸리는 시간은 답의 수에 비례한다.
2.가능한 모든 답의 후보를 만드는 과정을 여러 개의 선택으로 나눈다. 각 선택은 답 후보의 한 조각이다.
3.그 중 한 조각을 선택해 재귀 호출을 한다.
4.조각이 마지막이거나 없으면 기저 사례로 택한다.

## 6-3 문제 : 소풍(난이도 : 하)
&nbsp; 어느 유치원에서 소풍을 가는데 학생들을 두 명씩 짝 지어 행동하려고 한다. 그런데 서로 친구인 학생들끼리만 짝을 지어야 한다. 각 학생들의 쌍에 대해 이들이 서로 친구인지 여부가 주어질때, 학생들을 짝 지을 수 있는 방법을 구해라.
## 6-4 풀이
### 완전 탐색
&nbsp; 이렇게 가능한 조합의 수를 계산하는 문제를 푸는 가장 간단한 방법은 완전 탐색을 이용해 조합을 모두 만들어 보는 것이다. 재귀 호출을 이용해 문제를 풀려면 여러 개의 조각으로 나눠야 한다. 전체 문제를 N/2 개의 조각으로 나눠서 한 조각마다 두 학생을 짝지어 주는 것이다. 이때 문제는 아직 짝을 찾지 못한 학생들이 둘씩 짝짓는 경우의 수를 계산하는 것이다.    하지만 이렇게 하면 중복으로 세는 문제가 발생된다. 따라서 순서를 강제해서 각 단계에서 남아 있는 학생들 중 가장 번호가 빠른 학생의 짝을 찾아 주도록 하면 된다.
``` c++
int n;
bool areFriends[10][10];
// taken[i] = i번째 학생이 짝을 이미 찾았으면 true, 아니면 false
int countPairings(bool taken[10]){
    // 남은 학생들 중 가장 번호가 빠른 학생을 찾는다.
    int firstFree = -1;
    for(int i=0; i<n; i++){
    if(!taken[i]){
    firstFree = i;
    br  eak;
         }
    }
// 기저 사례 : 모든 학생이 짝을 찾았으면 한 가지 방법을 찾았으니 종료한다.
if(firstFree == -1) return 1;
int ret = 0;
// 이 학생과 짝지을 학생을 결정한다.
for( int pairWith = firstFree+1; pairWith < n; ++pairWith){
    if(!taken[pairWith]&& areFriends[firstFree][pairWith]]) {
        taken[firstFree] = taken[pairWith] =true;
        ret += countPairings(taken);
        taken[firstFree] = taken[pairWith] = false;
    }
}
return ret;
}
```
## 6-5 문제 : 게임판 덮기(난이도 : 하)
#### 설명 : H X W 크기의 게임판이 있다. 게임판은 검,흰 칸으로 구성된 격자 모양을 하고 있는데 이중 모든 흰 칸을 3칸짜리 L 모양블록으로 덮고 싶다. 회전은 가능하지만 겹치거나 검은 칸을 덮거나 밖으로 나가면 안된다. 게임판을 덮는 방법의 수를 계산하라.
## 6-6 풀이
#### 이 문제도 완전 탐색을 이용해 해결한다. 우선 흰 칸의 수가 3의 배수가 아닐 경우 답이 없으니 이 부분은 따로 처리한다. 흰 칸의 수를 3으로 나눠서 내려놓을 블록의 수 n을 얻은 뒤 재귀 호출을 이용한다.
그러나 이런식으로 덮을 수 있는 방법의 수를 셀 수는 없다는 것을 잘 알고 있다. 중복되기 때문에 답을 강제할 필요가 있다.
#### 가장 간편한 방법은 재귀 호출의 각 단계마다 빈 칸중에서 윗줄 왼쪽부터 칸을 덮도록 하는 것이다.
* ### 구현
#### 다음 코드는 재귀 호출 알고리즘을 보여준다.
1. 한 칸을 덮는 4가지 방법을 각각의 코드로 구현하는 것이 아니라 coverType[] 배열에 저장한다. 이 배열은 4가지 방법에서 새로 채워질 칸들의 상대 좌표(y,x)로 저장한다.    
2. set()은 delta에 따라 블록을 놓는 역할과 치우는 역할을 한다.
3. set()은 해당 위치에 블록을 놓을 수 있는지 여부를 판단한다. 이때 곧장 함수를 종료하는 것이 아니라 마지막까지 함수를 실행한다.
```c++  
// 주어진 칸을 덮을 수 있는 4가지 방법
// 블록을 구성하는 세 칸의 상대적 위치(dy,dx)의 목록
const int coverType[4][3][2] ={
{ { 0,0 } , { 1,0 } . { 0,1} },
{ { 0,0 } , { 0,1 } . { 1,1} },
{ { 0,0 } , { 1,0 } . { 1,1} },
{ { 0,0 } , { 1,0 } . { 1,-1} },
};
// board의 (y,x)를 type번 방법으로 덮거나, 덮었던 블록을 없앤다.
// delta =1이면 덮고, -1이면 덮었던 블록을 없앤다.
// 만약 블록이 제대로 덮이지 않은 경우(게임판 밖으로 나가거나,겹치거나, 검은 칸을 덮을 때) false를 반환
bool set(vector<vector><int>& board, int y, int x, int type, int delta){
bool ok = true;
for(int i=0; i<3; i++){
const int ny = y + coverType[type][i][0];
const int nx = x + coverType[type][i][1];
if(ny<0 || ny >= board.size() || nx<0 || nx >= board[0].size())3
    ok = false;
 else if( (board[ny][nx] += delta) >1)
    ok = false;
  }
  return ok;
  }
  //board의 모든 빈 칸을 덮을 수 있는 방법의 수를 반환한다.
  //board[i][j] =1 이미 덮인 칸 혹은 검은 칸
  //board[i][j] =0 아직 덮이지 않은 칸
  int cover(vector<vector<int> >& board) {
      // 아직 채우지 못한 칸 중 가장 윗줄 왼쪽에 있는 칸을 찾는다.
      int y= -1, x= -1;
      for(int i=0; i<board.size(); i++)
        for(int j=0; j<board.size(); j++)
              if(board[i][j] == 0){
                y = i;
                x = j;
                break;
              }
              if(y != -1) break;
}
//기저 사례 : 모든 칸을 채웠으면 1을 반환한다.
if(y== -1) return 1;
int ret =0;
for(int type =0; type<4; ++type){
   // 만약 board[y][x]를 type 형태로 덮을 수 있으면 재귀 호출한다.
   if(set(board,y,x,type,1))
       ret += cover(board)
   // 덮었던 블록을 치운다.
   set(board,y,x,type,-1);
}
return ret;
}
```
> #### 내생각 구현해보기
#### num[H][W]로 2차배열 생성 ->0,1로 검,흰 나누기 -> 흰색인 부분 1만 따로 봄-> 1의 개수가 3의 배수가 아니면 false
#### 흰색 칸 첫부분부터 시작함  상하좌우 중 상부터 확인 (흰색이면 채우기) ->상으로 갔다면 간 곳에서 좌우만 흰색인지 확인(좌부터 흰색인지 확인)
#### 상하로 움직일 경우 다음은 좌우만 가능/ 좌우로 움직인 경우 상하로 가능 옮긴 곳은 전부 검은색으로 변함 
#### 만약 3칸을 칠했다면  기저 사례로 다시 흰색바탕으로 시작함
#### 만약 3칸을 칠하지 못했다면 칠할수없기 떄문에 FALSE
### 풀이
## 6-7 최적화 문제
#### 지금까지 다뤘던 문제와는 달리 문제의 답이 하나가 아니라 여려 개이고, 그 중 어떤 기준에 따라 가장 좋은 답을 찾아내는 문제들을 최적화 문제(Optimization problem)라고 부른다. 예를 들어 n개 중 r개를 순서 없이 골라내는 것은 최적화 문제가 아니다. 무게의 합을 최대화하거나 최소화하는 이런 문제들을 최적화 문제라고 한다.그 중 가장 기초적인 방법이 완전 탐색이다.
* ### 예제 : 여행하는 외판원 문제
#### 가장 유명한 최적화 문제 중 하나로 외판원 문제(Traveling Sales-man Problem, TSP)가 있다. 어떤 나라에 n개의 큰 도시가 있다. 한 도시에서 출발해 다른 도시들을 전부 한번씩 방문한 뒤 시작위치로 돌아오려고 한다. 이 중 가장 짧은 경로를 찾는 방법?
#### 이 문제는 재귀 호출을 이용해 풀 수 있다. n개의 도시로 구성된 경로를 n개의 조각으로 나눠, 앞에서부터 도시를 하나씩 추가해 경로를 만든다.
> shortestPath(path) = path가 지금까지 만든 경로일 때, 나머지 도시들을 모두 방문하는 경로들 중 가장 짧은 것의 길이를 반환한다.
``` c++
int n; 도시의 수
double dist[MAX][MAX]; // 두 도시 간의 거리를 저장하는 배열
// path : 지금까지 만든 경로
// visited : 각 도시의 방문 여부
// currentLength : 지금까지 만든 경로의 길이
// 나머지 도시들을 모두 방문하는 경로들 중 가장 짧은 것의 길이를 반환한다.
double shortestPath(vector<int>& path, vector<bool> & visited, double currentLength){
if(path.size() == n) //기저 사례 : 모든 도시를 다 방문했을 때 시작 도시로 돌아가고 종료
    return currentLength + dist[path[0][path.back()];
double ret = INF; // 매우 큰값으로 초기화
// 다음 방문할 도시를 전부 시도해 본다.
for(int next = 0; next< n ; ++next){
    if(visited[next]) continue;
    int here = path.back();
    path.push_back(next);
    visited[next] = true;
    // 나머지 경로를 재귀 호출을 통해 완성하고 가장 짧은 경로의 길이를 반환한다
    double cand = shortestPath(path,visited,currentLength + dist[here][next]);
    ret = min(ret,cand);
    visited[next] = false;
    path.pop_back();
}
return ret;
}
```
## 6-8 시계 맞추기( 난이도 : 중)
#### 4X4개의 격자 형태로 배치된 16개의 시계가 있다. 이 시계들은 모두 12,3,6,9시를 가리키는데, 모두 12시로 바꾸고 싶다. 시계를 조작하는 방법은 스위치를 조작해야 하며 스위치는 3~5개까지 연결되어 있다. 최소한으로 스위치를 누르는 횟수를 계산하라.
## 6-9 풀이
``` c++
const int INF = 9999, SWITCHES = 10, CLOCKS =16;
//linked[i][j] = 'x' : i번 스위치와 j번 시계가 연결됨
//linked[i][j] = '.' : i번 스위치와 j번 시계가 연결되지 않음
const char linked[SWITCHES][CLOCKS+1] = {
// 0123456789012345
   "xxx............"
   "..x...x...x...."
   "xx......x.x...."
   "xxx.......x...."
   "xx....x......x."
   "xx.....x.....x."
   "xxx..x........."
   "xx..x.......x.."
   "x.....x....x..."
   }
// 모든 시계가 12시를 가리키는지 확인한다.
bool areAligned(const vector<int>& clocks);
// swtch번 스위치를 누른다.
void push(vector<int>& clocks, int swtch) {
    for(int clock = 0; clocks < CLOCKS; ++clocks)
        if(linked[swtch][clock] =='x') {
            clocks[clock] += 3;
            if(clocks[clock] == 15) clocks[clock] = 3;
        }
}
// clocks : 현재 시계들의 상태
// swtch : 이번에 누를 스위치의 번호
// 남은 스위치들을 눌러서 clocks를 12시로 맞출 수 있는 최소 횟수를 반환한다.
// 만약 불가능하다면 INF 이상의 큰 수를 반환한다.
int solve(vector<int> & clocks, int swtch){
 if(swtch == SWITCHES) return areAligned(clocks) ? 0 : INF;
// 이 스위치를 0번 누르는 경우부터 3번 누르는 경우까지 모두 시도한다.
int ret = INF;
for(int cut = 0; cnt < 4 ;cnt++){
    ret = min(ret, cnt+solve(clocks,swtch +1));
    push(clocks, swtch);
}
// push(clocks,swtch)가 4번 호출되었으니 clocks는 원래 상태가 된다.
return ret;
}
```
## 6-10 많이 등장하는 완전 탐색 유형
#### 순열 만들기 :서로 다른 N개의 원소를 일렬로 줄 세운 것을 순열(permutation)이라고 한다. 또한 서로 다른 N개의 원소 중에서 R개를 순서 없이 골라 낸 것을 조합(combination)이라고 한다.
n개의 질문에 대한 답이 예/아니오 둘 중 하나라고 할때 답의 모든 조합의 수는 2^n 가지이다.
