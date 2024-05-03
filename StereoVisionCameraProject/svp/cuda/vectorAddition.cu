#include <stdio.h>
#include<iostream>

#define N 10000000
__global__
void vector_add(float *out, float *a, float *b, int n) {
    for(int i = 0; i < n; i++){
        out[i] = a[i] + b[i];
    }
}

int main(){
    float *a, *b, *out; 
    float *d_a;

    // Allocate memory
    a   = (float*)malloc(sizeof(float) * N);
    b   = (float*)malloc(sizeof(float) * N);
    out = (float*)malloc(sizeof(float) * N);

    //Allocater GPU memory
    cudaMalloc((void**)&d_a, sizeof(float)*N);

    //Copy data from cpu to GPU
    cudaMemcpy(d_a, a, sizeof(float)*N, cudaMemcpyHostToDevice);

    // Initialize array
    for(int i = 0; i < N; i++){
        a[i] = 1.0f; b[i] = 2.0f;
    }

    cudaError_t err = cudaGetLastError();  // add
    if (err != cudaSuccess) 
        std::cout << "CUDA error: " << cudaGetErrorString(err) << std::endl; // add
    //cudaProfilerStop();
    // Main function
    vector_add<<<1,1>>>(out, d_a, b, N);
    free(a);
    free(b);
    free(out);
    cudaFree(d_a);
    printf("worked");
    return 0;
}