# You can find the new timestamped tags here: https://hub.docker.com/r/gitpod/workspace-full/tags
FROM gitpod/workspace-full:latest

RUN brew install kubectl
RUN brew install helm
RUN brew install okteto