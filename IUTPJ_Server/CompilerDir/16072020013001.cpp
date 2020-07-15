/*---------------Go Code GO---------------*/

#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp>
#include <ext/pb_ds/tree_policy.hpp>

using namespace std;
using namespace __gnu_pbds;

template <typename T>
using Order_Set = tree<T, null_type, less<T>, rb_tree_tag, tree_order_statistics_node_update>;
//(order) Set.order_of_key(); (pointer) Set.find_by_order();

#define PI acos(-1.0)
#define O_O                           \
    ios_base::sync_with_stdio(false); \
    cin.tie(NULL)
#define precision(a) fixed << setprecision(a)
#define endl '\n'
#define Flush cout << flush
#define LLMX 0x3fffffffffffffff

void Solution();

int main()
{
    O_O;

    long long tst = 1;

    cin >> tst;
    while (tst--)
    {
        Solution();
    }
    return 0;
}

void Solution()
{
    long long N, k;
    cin >> N;
    long long arr[N + 2];
    for (int i = 1; i <= N; i++)
    {
        cin >> arr[i];
    }
    int l, m, r;
    for (int i = 1; i <= N; i++)
    {
        l = i;
        m = i;
        r = i;
        for (int j = i - 1; j > 0; j--)
        {
            if (arr[j] < arr[i])
                l = j;
        }
        for (int j = i + 1; j <= N; j++)
        {
            if (arr[j] < arr[i])
                r = j;
        }
        if (l < m && m < r)
        {
            cout << "YES" << endl;
            cout << l << ' ' << m << ' ' << r << endl;
            return;
        }
    }

    cout << "NO" << endl;
}